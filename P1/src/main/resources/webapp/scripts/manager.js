const urlParams = new URLSearchParams(window.location.search)
const employeeID = urlParams.get("id");
let reimbursementCont;
let reimbID;

window.onload = async function(){

    const sessionRes = await fetch(`${domain}/api/checkSession`);
    const sessionUser = await sessionRes.json();
    console.log(sessionUser);

    if(sessionUser.response.user_id == employeeID){
        let reimbursementsRespAll = await fetch(`${domain}/api/manager?id=${employeeID}`)
        let reimbursementsDataAll = await reimbursementsRespAll.json();
        populateData(reimbursementsDataAll);

        
        document.getElementById("all-requests").onclick = async function(){
            reimbursementsRespAll = await fetch(`${domain}/api/manager?id=${employeeID}`)
            reimbursementsDataAll = await reimbursementsRespAll.json();
            let oldInfo = document.getElementById("Reimbursement");
            oldInfo.innerHTML = '';
            populateData(reimbursementsDataAll)
        }

        document.getElementById("pending-requests").onclick = async function(){
            let reimbursementsResp = await fetch(`${domain}/api/manager/pending`)
            let reimbursementsData = await reimbursementsResp.json();
            let oldInfo = document.getElementById("Reimbursement");
            oldInfo.innerHTML = '';
            populateData(reimbursementsData)
        }

        document.getElementById("accepted-requests").onclick = async function(){
            let reimbursementsRespAccepted = await fetch(`${domain}/api/manager/accepted`)
            let reimbursementsDataAccepted = await reimbursementsRespAccepted.json();
            let oldInfo = document.getElementById("Reimbursement");
            oldInfo.innerHTML = '';
            populateData(reimbursementsDataAccepted)
        }

        document.getElementById("denied-requests").onclick = async function(){
            let reimbursementsRespDenied = await fetch(`${domain}/api/manager/denied`)
            let reimbursementsDataDenied = await reimbursementsRespDenied.json();
            let oldInfo = document.getElementById("Reimbursement");
            oldInfo.innerHTML = '';
            populateData(reimbursementsDataDenied)
        } 

        document.getElementById("logout").onclick = function(){
            window.location = `${domain}/`
        }
    }    else{
        window.location = `${domain}/`;
    }
}


function convertTime(time){ // Code found on stack overflow, provided by user Dan Alboteanu
    var s = new Date(time).toLocaleTimeString("en-US")
    return s;
}

function convertDate(time){ //Code found on stack Overflow, provided by user Dan Alboteanu
    var s = new Date(time).toLocaleDateString("en-US")
    return s;
}

function populateData(reimbursementData){
    let reimbElem;
    let i=0;
    reimbursementCont = document.getElementById("Reimbursement")
    reimbursementData.response.forEach(reimbursement =>{
        
        if(reimbursement.reimbStatus == "pending"){
            reimbElem = document.createElement("div");
            reimbElem.className = "reimbursementY";
        }
        else if(reimbursement.reimbStatus == "denied"){
            reimbElem = document.createElement("div");
            reimbElem.className = "reimbursementR";
        }
        else{
            reimbElem = document.createElement("div");
            reimbElem.className = "reimbursementG";
        }

        let empName = document.createElement("span");
        empName.className = "info"; 
        empName.innerText = reimbursement.reimbAuthorString;

        let reimbAmt = document.createElement("span");
        reimbAmt.className = "info";
        reimbAmt.innerText = reimbursement.reimbAmt.toFixed(2);

        let reimbDescription = document.createElement("span");
        reimbDescription.className = "info";
        reimbDescription.innerText = reimbursement.description;

        let reimbStatus = document.createElement("span");
        reimbStatus.className = "info";
        reimbStatus.innerText = reimbursement.reimbStatus;

        let reimbsubmitted = document.createElement("span");
        reimbsubmitted.className = "info";
        reimbsubmitted.innerText = convertTime(reimbursement.submitted) + " " + convertDate(reimbursement.submitted);

        let reimbResolved = document.createElement("span");
        reimbResolved.className = "info";
        let reimbManager = document.createElement("span");
        reimbManager.className = "info";
        if(reimbursement.resolved == null){
            reimbResolved.innerText = "N/A";
            reimbManager.innerText = "N/A";
        }
        else{
            reimbResolved.innerText = convertTime(reimbursement.resolved) + " " + convertDate(reimbursement.resolved);
            reimbManager.innerText = reimbursement.reimbResolverString;
        }

        let reimbType = document.createElement("span");
        reimbType.className = "info";
        reimbType.innerText = reimbursement.reimbType;


        let approveBtn = document.createElement("button");
        approveBtn.className = "btn btn-success";
        approveBtn.id = "abuttons"
        approveBtn.innerText = "Accept";

        let denyBtn = document.createElement("button");
        denyBtn.id = "dbuttons"
        denyBtn.className = "btn btn-danger";
        denyBtn.innerText = "Deny";


        approveBtn.addEventListener('click', async function(){
            reimbID = reimbursement.reimbID;
            let acceptReq = await fetch(`${domain}/api/manager?userid=${employeeID}&id=${reimbID}`, {
            method:"POST"})
            window.location = `${domain}/managerpage?id=${employeeID}`;
        })

        denyBtn.addEventListener('click',async function(){
            reimbID = reimbursement.reimbID;
            let denyReq = await fetch(`${domain}/api/manager?userid=${employeeID}&id=${reimbID}`, {
            method:"PATCH"})
            let denyReqData = await denyReq.json();
            window.location = `${domain}/managerpage?id=${employeeID}`;
        })

        

        
        reimbElem.appendChild(empName);
        reimbElem.appendChild(reimbAmt);
        reimbElem.appendChild(reimbDescription);
        reimbElem.appendChild(reimbStatus);
        reimbElem.appendChild(reimbsubmitted);
        reimbElem.appendChild(reimbResolved);
        reimbElem.appendChild(reimbManager);
        reimbElem.appendChild(reimbType)
        reimbElem.appendChild(approveBtn);
        reimbElem.appendChild(denyBtn);
        reimbursementCont.appendChild(reimbElem);
        let space = document.createElement("br");
        reimbursementCont.appendChild(space);

        
    }) 
}