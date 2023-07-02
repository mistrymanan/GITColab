import { useSelector } from "react-redux";
import "./landing.css";
import { selectUser } from "../../redux/userSlice";
import { Outlet } from "react-router-dom";

const Landing = () => {
    const isAuth = useSelector(selectUser);

    return (
        <>
            {isAuth ? (
                <Outlet />
            ) : (
                <div className='landing'></div>
            )}
        </>
    )
}

export default Landing;