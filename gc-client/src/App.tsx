import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'

import './App.css';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import {Container} from 'react-bootstrap';
import Profile from './view/Profile/Profile';
import Dashboard from './view/Dashboard/Dashboard';
import Explore from './view/Explore/Explore';
import Integration from './view/Integration/Integration';
import Landing from './view/Landing/Landing';
import HomeScreen from './view/HomeScreen/HomeScreen';



function App() {

  return (
    <Router>
      <Header/>
        <Container>
          {/* Refactor later */}
            <Routes>
              <Route path='/' Component={HomeScreen}/>
              <Route path='/profile' Component={Profile}/>
              <Route path='/dashboard' Component={Dashboard}/>
              <Route path='/explore' Component={Explore}/>
              <Route path='/integration' Component={Integration}/>
              <Route path='/landing' Component={Landing}/>

            </Routes>
        </Container>

      <Footer/>
    </Router>
  );
}

export default App;
