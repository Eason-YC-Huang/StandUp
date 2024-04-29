import {useEffect, useState} from 'react'
import axios from "axios";

function App() {
    const [msg, setMsg] = useState("");

    useEffect(() => {
        axios.get(('/api/greeting'))
            .then(res => res.data)
            .then(data => setMsg(data));
    }, []);

    return (
        <>
            <h1 className={"text-red-500"}>{msg}</h1>
        </>
    );
}

export default App
