import { Card, CardGroup, Col, Container, Row } from "react-bootstrap";
import DashboardCard from "../../components/ui/Dashboard/Card";

const Dashboard = () => {
    return (
        <>
            <Container className="mt-3">
                <div className="d-flex flex-row justify-content-between py-3">
                    <h1>Dashboard</h1>
                </div>
                <CardGroup>
                    <Card>
                        <Card.Body>
                            <Card.Title>Total Users</Card.Title>
                            <Card.Text>100</Card.Text>
                        </Card.Body>
                    </Card>
                    <Card>
                        <Card.Body>
                            <Card.Title>Total Projects</Card.Title>
                            <Card.Text>50</Card.Text>
                        </Card.Body>
                    </Card>
                    <Card>
                        <Card.Body>
                            <Card.Title>Other System Detail</Card.Title>
                            <Card.Text>Value</Card.Text>
                        </Card.Body>
                    </Card>
                    <Card>
                        <Card.Body>
                            <Card.Title>Another Detail</Card.Title>
                            <Card.Text>Value</Card.Text>
                        </Card.Body>
                    </Card>
                </CardGroup>

                <Row className="mt-4">
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title>Chart 1</Card.Title>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title>Chart 2</Card.Title>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
                <Row className="mt-4">
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title>Chart 3</Card.Title>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title>Chart 4</Card.Title>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </>
    )
}
export default Dashboard;