
    //get the bar chart canvas
    var ctx = $("#bar-chartcanvas");

    const b_data= {};
    fetch('../textfiles/browser.txt')
    .then(response => response.text())
    .then(data => {
        data.split("\n").map(e=> ( e!="")? !!b_data[e] ? b_data[e]++ : b_data[e]=1: "")
        createChart(b_data)
    });
  

function createChart(data){
    let data_l= Object.keys(b_data)
    let data_d= Object.values(b_data)
    
    //bar chart data
    var data = {
      labels: data_l,
      datasets: [
        {
          label: "As per Time",
          data: data_d,
          backgroundColor: [
            "#F1AFA8",
            "#C39BD3",
          ],
          borderWidth: 1
        },
]
    };
  
    //options
    var options = {
      responsive: true,
      title: {
        display: true,
        position: "top",
        text: "Bar Graph",
        fontSize: 18,
        fontColor: "#111"
      },
      legend: {
        display: true,
        position: "bottom",
        labels: {
          fontColor: "#333",
          fontSize: 16
        }
      },
      scales: {
        yAxes: [{
          ticks: {
            min: 0
          }
        }]
      }
    };
  
    //create Chart class object
    var chart = new Chart(ctx, {
      type: "bar",
      data: data,
      options: options
    });
}
  
