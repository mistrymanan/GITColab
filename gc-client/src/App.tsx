import React from 'react';

import './App.css';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import {Container} from 'react-bootstrap';

function App() {
  return (
    <>
    <Header/>
      <Container>
        <main>
            Page Content
        </main>
      </Container>
    <Footer/>
    </>
  );
}

export default App;
