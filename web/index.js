let socket;

const ready = () => {
    console.log("Establishing WebSocket")
    socket = new WebSocket("ws://localhost:8080/")

    socket.onmessage = (event) => {
        console.dir(event)
    }

    socket.onopen = () => {
        console.log("Ready")
        socket.send(`{ "message": "mero" }`)
    }

}

window.onload = ready
