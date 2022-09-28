/*
    What is the Fetch API?
    - Fetch API is a modern approach to making asynchronous HTTP requests and allows us to make those requests by using promises to retrieve data from our server/external API
    - Why use FETCH over AJAX?
        - much simplier and clean way of making HTTP request in your JS code
*/

//target the button that will trigger the HTTP request upon clicking
const button = document.getElementById("btn");

//add an eventlistener to my button
button.addEventListener('click', async() => {
//target the input field that holds the pokemon's id number
var id = document.getElementById("company-field").value;


//do HTTP request and send it to the server using Fetch API
try {
    //get the promise that is returned in the HTTP response by calling the fetch() function
    //Note: the fetch function will take some time to get the data, therefore we must use the await keyword here
    const raw_response = await fetch(`https://www.themuse.com/api/public/jobs?category=${id}&page=1`); //this will return a Promise

        
    
    //check for a successful response
    //here we can use our raw_response variable to do the same things as we did with the XHR object when we used AJAX

    //if the response doesn't return as a 200, this code block will execute
    if(!raw_response.ok){
        throw new Error(raw_response.status);
    }

    //if successful, then the following code will execute
    //here I would want to save the JSON data that came back in the response
    const json_data = await raw_response.json();
    console.log(json_data);

    //append the response data to my webpage
    renderHTML(json_data);
} catch (error) {
    //this catch block will execute if there is a network error
    console.log(error);
}
});

function renderHTML(json_data){
//target the p tag that will hold the response data
let responseArea = document.getElementById("input");



let result = json_data.results;
responseArea.append("Currently hiring for: ");
responseArea.append(document.createElement("br"));
responseArea.append(document.createElement("br"));
//first loop go through result 
result.forEach(element=>{
    //append name of postion 
    responseArea.append(`Postion: ${element.name}`);
    responseArea.append(document.createElement("br"))
    responseArea.append(`Posted on: ${new Date(element.publication_date)}`);
    responseArea.append(document.createElement("br"))
    
    //append location of job post
    let location = element.locations;
    location.forEach(element=>{
        responseArea.append(`Location: ${element.name}`);
        responseArea.append(document.createElement("br"))
    });
    //append industry of job post 
    let category = element.categories;
    category.forEach(element=>{
        responseArea.append(`Industry: ${element.name}`);
        responseArea.append(document.createElement("br"))
    });
    //append description of job post
    responseArea.append(`Description: ${element.contents}`);
    responseArea.append(document.createElement("br"))
    
    
    responseArea.append(document.createElement("br"))
    
    let ref = element.refs;
    for(key in ref) {
        
            responseArea.append(`For more info: ${ref[key]}`);
      }

      responseArea.append(document.createElement("br"));    
}
)}