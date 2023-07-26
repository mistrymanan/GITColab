import { useSelector } from "react-redux";
import "./landing.css";
import { selectUser } from "../../redux/userSlice";
import { Navigate, Outlet } from "react-router-dom";

const Landing = () => {
    const isAuth = useSelector(selectUser);

    return (
        <>
            {isAuth ? (
                <Outlet />
            ) : (
                <>
                    <Navigate to="/" />
                    <div className='landing'></div>
                </>
            )}
        </>
    )
}

export default Landing;