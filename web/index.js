let socket;
let obj = []
let canvas
let ctx
let id

const draw = () => {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    obj.forEach(o => {
        ctx.fillStyle = o.color
        ctx.fillRect(o.x, o.y, 50, 50)
    })

    requestAnimationFrame(draw)
}

const handleMove = (x, y) => {
    socket.send(JSON.stringify({
        event: 'move',
        id,
        x: obj.filter(o => o.id === id)[0].x + x,
        y: obj.filter(o => o.id === id)[0].y + y
    }))
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
            case "self":
                id = data.id
                break
            default:
                console.error("Unknown event :", data)
        }
    }

    socket.onopen = (e) => {
        console.log("Connection established.")
    }

    requestAnimationFrame(draw)

    document.querySelector('body')
        .addEventListener('keydown', event => {
            switch (event.key) {
                case "ArrowUp":
                    handleMove(0, -10)
                    break
                case "ArrowDown":
                    handleMove(0, 10)
                    break
                case "ArrowLeft":
                    handleMove(-10, 0)
                    break
                case "ArrowRight":
                    handleMove(10, 0)
                    break
            }
        })
}

window.onload = ready
