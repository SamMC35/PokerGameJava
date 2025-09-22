async function startGame(event){
  console.log("Let's a go!!!!!!!");
  event.preventDefault();

  try{
    const currentUrl = "http://" + window.location.hostname + ":" + window.location.port
    const response = await fetch(currentUrl + "/startGame", {
      method: "POST"
    })


    if(response.ok){
      window.location.href = "/table"
    }
  } catch(err){
    console.error("Error in pushing: " + err.message)
  }
}

async function fetchData() {
  const currentUrl = "http://" + window.location.hostname + ":" + window.location.port
  const response = await fetch(currentUrl + '/getPlayers')

  console.log("Response: " + response);

  if (response.ok) {
    const data = await response.json();

    const tableBody = document.querySelector('#data-table tbody')

    // Keep old player list (to detect new ones)
    const existingNames = Array.from(tableBody.querySelectorAll("td"))
      .map(td => td.textContent.trim());

    // Clear table
    tableBody.innerHTML = ''

    data.forEach(element => {
      const row = document.createElement('tr')
      row.innerHTML = `
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
          ${element.name}
        </td>
      `

      // Animate if it's new
      if (!existingNames.includes(element.name)) {
        row.classList.add(
          "transition", "transform", "duration-500", "ease-out",
          "opacity-0", "translate-y-2"
        )
        // trigger animation in next frame
        requestAnimationFrame(() => {
          row.classList.remove("opacity-0", "translate-y-2")
        })
      }

      tableBody.appendChild(row)
    })
  } else {
    console.error("Failed to fetch data", response.status)
    showPopup(response.message)
  }

}

function showPopup(message) {
  const popup = document.createElement("div");
  popup.className =
    "fixed bottom bg-green-600 text-white px-4 py-2 rounded-lg shadow-lg animate-bounce";
  popup.textContent = message;

  document.body.appendChild(popup);

  setTimeout(() => {
    popup.remove();
  }, 3000); // remove after 3 seconds
}

window.onload = function () {
  setInterval(fetchData, 100) // fetch every 100ms
}


