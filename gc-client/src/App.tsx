
import {BrowserRouter as Router} from 'react-router-dom'

import FrontEndRoutes from './frontendroutes/FrontEndRoutes';
import {Container} from 'react-bootstrap';



function App() {

  return (
    <Router>
        <Container style={{maxWidth:'100vw', padding:3}}>
          <FrontEndRoutes/>
        </Container>
    </Router>
  );
}

export default App;
