
// async function getResponce(){
//     let response = await fetch('http://localhost:8080/stat/get')
//     let content = await response.json();
//     console.log(content);
// }

// getResponce();

// fetch('http://localhost:8080/stat/get').then(responce => responce.json).then(responce => console.log(responce))

// document.addEventListener("DOMContentLoaded", () => {

//     console.log("Start");
//     const xhr = new XMLHttpRequest();
//     xhr.open("GET", "http://localhost:8080/stat/get");
//     xhr.send();
//     xhr.responseType = "json";
//     xhr.onload = () => {
//         if (xhr.readyState == 4 && xhr.status == 200) {
//             const data = xhr.response;
//             console.log(data);
//         } else {
//             console.log(`Error: ${xhr.status}`);
//         }
//     };
// });



// $(document).ready(function () {
//     $.ajax({
//         url: "http://0.0.0.0:8080/stat/get",
//         type: "GET",
//         dataType: "json",

//         success: function (data) {
//             console.log(data);
//         },
//         error: function (error) {
//             console.log(error);
//         }
//     })
// });

//   console.log("Finished");
// const buttons = document.querySelectorAll(".super-link");
// buttons.forEach(button => {
//     button.addEventListener("click", () => {
//         buttons.forEach(btn => btn.classList.remove("active-butt"));
//         button.classList.add("active-butt");
//     });
// });
// });

