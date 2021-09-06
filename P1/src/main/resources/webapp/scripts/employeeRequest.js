const urlParams = new URLSearchParams(window.location.search)
const employeeID = urlParams.get("id");

let requestForm = document.getElementById("request-form");
let type;

window.onload = async function(){
    document.getElementById("back").onclick = function(){
      window.location = `${domain}/employeepage?id=${employeeID}`;
    } 
}
requestForm.onsubmit = async function(e){
    e.preventDefault();

    let amount = document.getElementById("amount").value;
    let description = document.getElementById("description").value;
    let options = document.getElementsByName("reimbType");
    options.forEach(button =>{
        if(button.checked){
            type = button.value;
        }
    })
    
    let serverResponse = await fetch(`${domain}/api/employee?id=${employeeID}`,{
        method: "POST",
        body: JSON.stringify({
            reimbID: 0,
            reimbAmt: amount,
            submitted: Date.now,
            resolved: Date.now,
            description: description,
            reimbAuthor: employeeID,
            reimbResolver: 0,
            reimbStatusInt: 2,
            reimbTypeInt: type

        })
    })

    let responseData = await serverResponse.json();
    if(responseData.success){
        alert("Submission successful, redirecting back to main employee page");
            window.location = `${domain}/employeepage?id=${employeeID}`
    }
    else{
        alert("Submission failed. Make sure all the boxes have been filled in")
            window.location = `${domain}/addrequest?id=${employeeID}`
       
    }
}