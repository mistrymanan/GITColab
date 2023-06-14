import React from 'react';

import './App.css';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import {Container} from 'react-bootstrap';
import Profile from './view/Profile/Profile';

function App() {
  return (
    <>
    <Header/>
      <Container>
        <Profile/>
      </Container>
    <Footer/>
    </>
  );
}

export default App;
