import { Card, Button } from "react-bootstrap"

const CardComponent = (props: any) => {
  return (
    <Card className="my-3 p-3 rounded" border="secondary" style={{ width: '18rem' }}>
      <a href="#" target="_blank" rel="noopener noreferrer">
          <Card.Img variant="top" src="https://1000logos.net/wp-content/uploads/2021/05/GitHub-logo.png" alt="Repo Image" />
      </a>
      <Card.Body>
          <Card.Title><h4>GitColab</h4></Card.Title>
          <Card.Text className="my-1">keyurkhant</Card.Text>
          <Card.Text as='p'>Test our Cards</Card.Text>
          <Card.Text as='p'>
            5 <i className="fas fa-solid fa-star" />'s
            5 <i className="fas fa-solid fa-code-branch" />'s
            5 <i className="fas fa-solid fa-exclamation-circle" />'s
          </Card.Text>
          
          <Button variant="primary" href="#" target="_blank">Send Request</Button>
      </Card.Body>
    </Card>
  )
}

export default CardComponent;