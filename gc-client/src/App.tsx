
import {BrowserRouter as Router} from 'react-router-dom'

import PageRoutes from './routes/routes';
import {Container} from 'react-bootstrap';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';

const App = () => {
  return (
    <Router>
        <Container style={{maxWidth:'100vw', padding:0}}>
          <Header />
          <PageRoutes />
          {/* <Footer /> */}
        </Container>
    </Router>
  );
}

export default App;
