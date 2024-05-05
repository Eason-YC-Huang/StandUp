import {createBrowserRouter} from "react-router-dom";
import {useRef} from "react";
import axios from "axios";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>
    }
]);


function App() {

    const minutes = useRef(0);

    const applyAction = () => {
        axios.get("/api/stand-up", {params: {minutes: minutes.current}})
            .then(res => res.data)
            .then(data => alert(data));
    }

    const confirmAction = () => {
        axios.get("/api/stand-up/yes")
            .then(res => res.data)
            .then(data => alert(data));
    }

    const cancelAction = () => {
        axios.get("/api/stand-up/no")
            .then(res => res.data)
            .then(data => alert(data));
    }


    return (
        <main className={"w-screen "}>
            <div className={"h-screen flex flex-col justify-center gap-y-4"}>
                <div className={"mx-auto flex gap-x-2"}>
                    <input type="text" placeholder={"Take a break after x mintues"}
                           className={"border-2 px-4 py-2 rounded-2xl border-blue-500 focus:outline-none w-80"}
                           onChange={e => minutes.current = Number(e.target.value)}
                    />
                    <button
                        className={"border-2 px-4 py-2 rounded-2xl border-blue-500 bg-blue-500 text-amber-50 hover:bg-blue-600 font-medium w-44"}
                        onClick={e => applyAction()}
                    >Apply
                    </button>
                </div>
                <div className={"mx-auto"}>
                    <div className={"text-center text-lg"}>
                        <p>
                            If this page pops up automatically, it means that you have reached your scheduled break
                            time.
                        </p>
                        <p>
                            Please click the 'Confirm' button to take a break, or click the 'Cancel' button to skip this
                            rest.
                        </p>
                        <p>
                            If no action is taken, your screen will be locked automatically in 30 seconds.
                        </p>
                    </div>
                    <div className={"flex justify-center gap-x-4 mt-4"}>
                        <button
                            className={"border-2 px-4 py-2 rounded-2xl bg-blue-500 focus:outline-none hover:bg-blue-600 w-96 text-amber-50 font-medium"}
                            onClick={e => confirmAction()}
                        >Confirm
                        </button>
                        <button
                            className={"border-2 px-4 py-2 rounded-2xl bg-red-500 focus:outline-none hover:bg-red-600 w-96 text-amber-50 font-medium"}
                            onClick={e => cancelAction()}
                        >Cancel
                        </button>
                    </div>

                </div>
            </div>
        </main>
    );
}

export default App
