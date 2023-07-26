
import {BrowserRouter as Router} from 'react-router-dom'

import PageRoutes from './routes/routes';
import {Container} from 'react-bootstrap';
import Header from './components/Header/Header';

const App = () => {
  return (
    <Router>
        <Container style={{maxWidth:'100vw', padding:0}}>
          <Header />
          <PageRoutes />
        </Container>
    </Router>
  );
}

export default App;
