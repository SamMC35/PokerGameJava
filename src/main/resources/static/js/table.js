async function getTableData(){
    const currentUrl = "http://" + window.location.hostname + ":" + window.location.port

    console.log(currentUrl)
    const response = await fetch(currentUrl + "/getTableInfo", {
        method: "GET"
    })

    if(response.ok){
        const result = await response.json()

        const potDiv = document.getElementById("pot")
        potDiv.textContent = "Pot:" + result.pot

        const stateDiv = document.getElementById("tableState")
        stateDiv.textContent = "Table State:" + result.tableState

        const tableBody = document.querySelector('#data-table tbody')


        var playerData = result.playerList

        playerData.forEach(player => {
            const row = document.createElement('tr')

            row.innerHTML = `
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                ${player.name}
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                ${player.wallet}
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                ${player.isCurrentPlayer}
                </td>
            `
            tableBody.appendChild(row)
        })
    }
}



window.onload = getTableData