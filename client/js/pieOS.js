
var oilCanvas = document.getElementById("oilChart");

const o_data= {};
fetch('../textfiles/os.txt')
  .then(response => response.text())
  .then(data => {
      data.split("\n").map(e=> ( e!="")? !!o_data[e] ? o_data[e]++ : o_data[e]=1: "")
      createChart(o_data)
  });

function createChart(data){
    let data_l= Object.keys(o_data)
    let data_d= Object.values(o_data)
    
    var oilData = {
        labels: data_l,
        datasets: [
            {
                data: data_d,
                backgroundColor: [
                    "#EEB723",
                    "#817ECE",
                ]
            }]
    };
    
    var pieChart = new Chart(oilCanvas, {
      type: 'pie',
      data: oilData
    });
}

