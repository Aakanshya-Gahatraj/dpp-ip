
var oilCanvas = document.getElementById("oilChart");

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
    
    var oilData = {
        labels: data_l,
        datasets: [
            {
                data: data_d,
                backgroundColor: [
                    "#E7924C ",
                    "#137FF1",
                ]
            }]
    };
    
    var pieChart = new Chart(oilCanvas, {
      type: 'pie',
      data: oilData
    });
}

