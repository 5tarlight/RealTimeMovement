let socket;
let obj = []
let canvas
let ctx

const draw = () => {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    obj.forEach(o => {
        ctx.fillStyle = o.color
        ctx.fillRect(o.x, o.y, 50, 50)
    })

    requestAnimationFrame(draw)
}

const ready = () => {
    console.log("Establishing WebSocket...")
    socket = new WebSocket("ws://localhost:8080/")
    canvas = document.getElementById("canvas");
    ctx = canvas.getContext("2d")

    socket.onmessage = (event) => {
        const data = JSON.parse(event.data)

        switch (data.event) {
            // case "join":
            //     obj.push(data.box)
            //     break
            case "sync":
                obj = []

                Object.keys(data.boxes).forEach(k => {
                    obj.push({
                        id: k,
                        ...data.boxes[k]
                    })
                })

                break
            case "quit":
                // const index = obj.findIndex(o => o.id === data.id)
                // obj = [
                //     ...obj.slice(0, index),
                //     ...obj.slice(index + 1)
                // ]
                break
            default:
                console.error("Unknown event :", data)
        }
        console.dir(data)
        console.dir(obj)
    }

    socket.onopen = () => {
        console.log("Connection established.")
    }

    requestAnimationFrame(draw)
}

window.onload = ready
