import {createBrowserRouter} from "react-router-dom";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>
    },
    {
        path: "/videos",
        element: <div>This is videos page</div>
    }
]);


function App() {
    return (
        <>
            <h1>Hello World</h1>
        </>
    );
}

export default App
