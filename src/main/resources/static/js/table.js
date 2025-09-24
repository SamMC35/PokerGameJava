function getSuitSymbol(suit) {
    switch(suit) {
        case "CLUBS": return "♣";
        case "DIAMONDS": return "♦";
        case "HEARTS": return "♥";
        case "SPADES": return "♠";
        default: return "?";
    }
}

function getSuitColor(suit) {
    if (suit === "HEARTS" || suit === "DIAMONDS") {
        return "text-red-600"; // Tailwind red for ♥ ♦
    }
    return "text-black"; // Clubs/Spades
}

function getRank(rank){
    switch(rank){
        case "ACE": return "A";
        case "TWO": return "2";
        case "THREE": return "3";
        case "FOUR": return "4";
        case "FIVE": return "5";
        case "SIX": return "6";
        case "SEVEN": return "7";
        case "EIGHT": return "8";
        case "NINE": return "9";
        case "KING": return "K";
        case "QUEEN": return "Q";
        case "JACK": return "J";
        default: return "?";
    }
}

async function getTableData(){
    const currentUrl = "http://" + window.location.hostname + ":" + window.location.port

    console.log(currentUrl)
    const response = await fetch(currentUrl + "/getTableInfo", {
        method: "GET"
    })

    if(response.ok){

        const result = await response.json()

        console.log("Result: " + JSON.stringify(result))

        const potDiv = document.getElementById("tablePot")
        potDiv.textContent = "Pot:" + result.pot

        const stateDiv = document.getElementById("tableState")
        stateDiv.textContent = "Table State:" + result.tableState

        const tableBody = document.querySelector('#data-table tbody')

        tableBody.innerHTML = ""


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

        //Table cards

        const tableCards = document.getElementById("tableCards")
        
        tableCards.innerHTML = ""

        if(result.tableCards && Array.isArray(result.tableCards)){
            
            console.log("Cards: " + result.tableCards)

            result.tableCards.forEach(card => {
                    const symbol = getSuitSymbol(card.suit);
                    const color = getSuitColor(card.suit);
                    const rank = getRank(card.rank);

                    const cardDiv = document.createElement("div");
                    cardDiv.className =
                        "w-24 h-36 bg-white border-2 border-gray-800 rounded-lg flex flex-col items-center justify-between p-2 shadow-lg";

                    cardDiv.innerHTML = `
                        <div class="w-full text-left ${color}">${rank}${symbol}</div>
                        <div class="flex-grow flex items-center justify-center text-3xl ${color}">${symbol}</div>
                        <div class="w-full text-left ${color} transform rotate-180">${rank}${symbol}</div>
                    `;

                    tableCards.appendChild(cardDiv);
            })
        }
    }
}



window.onload = function() {
    this.setInterval(getTableData,100)
}