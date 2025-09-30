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
        case "TEN" : return "10";
        case "KING": return "K";
        case "QUEEN": return "Q";
        case "JACK": return "J";
        default: return "?";
    }
}


async function getPlayerData(){
    const playerString = await sessionStorage.getItem('playerJson')

    const currentUrl = "http://" + window.location.hostname + ":" + window.location.port

    const playerJson = JSON.parse(playerString)

    console.log(playerJson)

    const modalBlock = document.getElementById("modalBlock")
    const OkModal = document.getElementById('OkModal')

    OkModal.addEventListener('click', () => { modalBlock.classList.add('hidden')})
    
    try{
        const response = await fetch(currentUrl + "/getPlayer/" + playerJson.id)

        if(response.ok){
            const json = await response.json()
            const playerWalletDiv = document.getElementById("playerWallet")
            const playerNameDiv = document.getElementById("playerName")

            playerNameDiv.textContent = "Name: " + json.name
            playerWalletDiv.textContent = "Wallet: " + json.wallet

            console.log("JSON: " + json)

            const cardsDiv = document.getElementById("playerCards");
            cardsDiv.innerHTML = ""; // clear old cards

            if (json.cardList && Array.isArray(json.cardList)) {
                const cardsDiv = document.getElementById("playerCards");
                cardsDiv.innerHTML = "";

                json.cardList.forEach(card => {
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

                    cardsDiv.appendChild(cardDiv);
                });
            }

            const raiseLabel = document.getElementById('raiseLabel')
            raiseLabel.max = json.wallet
            // raiseLabel.value = json.wallet/2

            const maxLabel = document.getElementById('max-label')
            maxLabel.textContent = `Max Value: ${json.wallet}`

        }
    } catch(err){
        console.error("Error: " + err)
    }
}



async function processInput(input, raiseVal){
    const resultJsonString = await sessionStorage.getItem('playerJson')

    console.log(JSON.stringify(resultJsonString))

    const currentUrl = "http://" + window.location.hostname + ":" + window.location.port

    const playerJson = JSON.parse(resultJsonString)

    console.log(JSON.stringify(playerJson))


    try{
        const payload = JSON.stringify({id: playerJson.id, inputType: input, raiseValue: raiseVal})

        console.log("Payload: " + payload)

        var response = await fetch(currentUrl + "/processInput", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: payload
        })
        
        if(response.ok){
            console.log("Processed Input")
        } else {
            const modalBlock = document.getElementById("modalBlock")
            modalBlock.classList.remove('hidden')
        }
    }catch(err){
        console.error("Error: " + err)
    }
    
}

async function listenInput(input){
    if(input == 'RAISING'){
        const raiseBlock = document.getElementById('raiseBlock')
        raiseBlock.classList.remove('hidden')
    } else {
        processInput(input)
    }
}

window.onload = function() {
    setInterval(getPlayerData, 200)
}

const raiseBlock = document.getElementById('raiseBlock')
const raiseLabel = document.getElementById('raiseLabel')

const raiseOkModal = document.getElementById("RaiseOkModal")

raiseOkModal.addEventListener('click', () => {
    processRaise('RAISING', raiseLabel.value)
})

const raiseCancelModal = document.getElementById("RaiseCancelModal")

raiseCancelModal.addEventListener('click', () => {
    raiseBlock.classList.add('hidden')
})


raiseLabel.addEventListener('input', () => {
    const currentValueRaise = document.getElementById('current-value-raise')

    currentValueRaise.textContent = `Current Value: ${raiseLabel.value}`
})

