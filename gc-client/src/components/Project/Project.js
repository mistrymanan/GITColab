import { Card } from "react-bootstrap"

const Project = ({data}) => {

    return (
        <Card className="my-3 p-3 rounded " border="primary"  >
            {/*- Will change later but clicking each card should re-direct to the dashboard of that project*/}
            <a href={`/dashboard/${data._id}`}>
                <Card.Img src={data.image} variant="top"/>
            </a>  
            
            <Card.Body>
            
                <Card.Text className="my-1"><h4>{data.name}</h4></Card.Text> 
                <Card.Text><strong>{data.owner}</strong></Card.Text> 
                <Card.Text as='p'>{data.stars} <i className="fas fa-solid fa-star"/>'s</Card.Text> 
                <Card.Text as='p'>{data.description}</Card.Text> 
            </Card.Body>

        </Card>
    )
}

export default Project;