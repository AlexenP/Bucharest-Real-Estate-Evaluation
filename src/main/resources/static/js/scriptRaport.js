document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const reportId = urlParams.get('id');

    if (reportId) {
        getReportData(reportId);
    } else {
        alert('No report ID found.');
    }

    function getReportData(reportId) {
        fetch(`/get-report?reportId=${reportId}`)
            .then(response => response.json())
            .then(data => {
                console.log("Report Data:", data); // Debugging information
                const reportContent = document.getElementById('reportContent');

                if (!data || Object.keys(data).length === 0) {
                    reportContent.innerHTML = '<p>No data available for this report.</p>';
                    return;
                }

                const sections = [
                    { label: 'Report Name', value: data.name },
                    { label: 'Date', value: new Date(data.date).toLocaleDateString() },
                    { label: 'User', value: data.user ? data.user.username : 'N/A' },
                    { label: 'Area', value: data.area ? data.area.areaName : 'N/A' },
                    { label: 'Tip Imobil', value: data.tipImobil },
                    { label: 'Number of Rooms', value: data.numarCamere },
                    { label: 'Minimum Surface', value: data.suprafataMinima },
                    { label: 'Year of Construction', value: data.anConstructie },
                    { label: 'Facilities', value: data.facilitati ? data.facilitati.join(', ') : 'N/A' },
                ];

                sections.forEach(section => {
                    const reportSection = document.createElement('div');
                    reportSection.classList.add('report-section');

                    const label = document.createElement('label');
                    label.textContent = section.label + ':';

                    const value = document.createElement('span');
                    value.textContent = section.value !== undefined ? section.value : 'N/A';

                    reportSection.appendChild(label);
                    reportSection.appendChild(value);
                    reportContent.appendChild(reportSection);
                });

                // Set price values in the price bar
                document.getElementById('priceMin').textContent = data.pretMinimPerMp.toFixed(2) + ' €/mp';
                document.getElementById('priceAdjust').textContent = data.pretAjustatPerMp.toFixed(2) + ' €/mp';
                document.getElementById('priceMax').textContent = data.pretMaximPerMp.toFixed(2) + ' €/mp';

                // Set property price values in the property value bar
                document.getElementById('propertyPriceMin').textContent = data.pretMinimProprietate.toFixed(2) + ' €';
                document.getElementById('propertyPriceAdjust').textContent = data.pretMediuProprietate.toFixed(2) + ' €';
                document.getElementById('propertyPriceMax').textContent = data.pretMaximProprietate.toFixed(2) + ' €';

                // Render property listings
                const propertyListings = document.getElementById('propertyListings');
                propertyListings.innerHTML = ''; // Clear previous listings
                data.propertyListings.forEach(listing => {
                    const li = document.createElement('li');
                    const a = document.createElement('a');
                    a.href = listing.url;
                    a.textContent = `${listing.title} - ${listing.price}`;
                    a.target = '_blank'; // Open link in a new tab
                    li.appendChild(a);
                    propertyListings.appendChild(li);
                });

                // Parse prices and generate price distribution chart
                const prices = data.propertyListings.map(listing => {
                    const priceMatch = listing.price.match(/\d+/g);
                    return priceMatch ? parseInt(priceMatch.join('')) : NaN;
                }).filter(price => !isNaN(price));

                console.log("Parsed Prices:", prices);
                if (prices.length > 0) {
                    generatePriceDistributionChart(prices);
                } else {
                    console.error("No valid prices found.");
                }
            })
            .catch(error => {
                console.error('Error fetching report data:', error);
                document.getElementById('reportContent').innerHTML = '<p>Error fetching report data. Please try again later.</p>';
            });
    }

    function generatePriceDistributionChart(prices) {
        const ctx = document.getElementById('priceDistributionChart').getContext('2d');
        const minPrice = Math.min(...prices);
        const maxPrice = Math.max(...prices);
        const interval = (maxPrice - minPrice) / 10; // Changed to 10 intervals

        const priceRanges = [];
        for (let i = 0; i < 10; i++) {
            priceRanges.push([minPrice + i * interval, minPrice + (i + 1) * interval]);
        }

        const priceCounts = priceRanges.map(range => prices.filter(price => price >= range[0] && price < range[1]).length);

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: priceRanges.map(range => `${range[0].toFixed(2)} - ${range[1].toFixed(2)} €`),
                datasets: [{
                    label: 'Number of Properties',
                    data: priceCounts,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: 'Number of Properties'
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Price Range (€)'
                        }
                    }
                }
            }
        });
    }
});
