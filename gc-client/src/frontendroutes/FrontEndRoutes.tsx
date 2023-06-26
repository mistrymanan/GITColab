import { Routes, Route } from "react-router-dom";

import Profile from '../view/Profile/Profile';
import Dashboard from '../view/Dashboard/Dashboard';
import Explore from '../view/Explore/Explore';
import Integration from '../view/Integration/Integration';
import Landing from '../view/Landing/Landing';
import ForgotPassword from '../view/ForgotPassword/ForgotPassword';
import ResetPassword from '../view/ForgotPassword/ResetPassword';
import Login from '../view/Login/Login';
import Registration from "../view/Registration/Registration";

const FrontEndRoutes = () => {

    return (
        <>
            {/* Refactor later */}
            <Routes>
                <Route path='/' Component={Dashboard}/>
                <Route path='/profile' Component={Profile}/>
                <Route path='/dashboard' Component={Dashboard}/>
                <Route path='/explore' Component={Explore}/>
                <Route path='/integration' Component={Integration}/>
                <Route path='/landing' Component={Landing}/>
                <Route path='/ForgotPassword' Component={ForgotPassword}/>
                <Route path='/ResetPassword' Component={ResetPassword}/>
                <Route path='/Login' Component={Login}/>
                <Route path='/Registration' Component={Registration}/>
            </Routes>
        </>
    )



}

export default FrontEndRoutes;