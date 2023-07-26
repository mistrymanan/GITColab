import { Card, Col, Container, Row } from "react-bootstrap";
import { useSelector } from "react-redux";
import { selectUser } from "../../redux/userSlice";
import { useEffect, useState } from "react";
import { getDashboardData } from "../../services/ProjectService";
import BarChart from "../../components/ui/Charts/BarChart";

const Dashboard = () => {
    const userDataStore = useSelector(selectUser);
    const [dashboardData, setDashboardData] = useState({} as any);
    const [barChartLabels, setBarChartLabels] = useState([]);
    const [barChartValues, setBarChartValues] = useState([]);

    useEffect(() => {
        async function fetchDashboardData() {
            let response = await getDashboardData(userDataStore.token);
            if (response?.status === 200) {
                setDashboardData(response?.data);
                const topCommittedRepositories = response?.data.topCommittedRepositories;
                const repositoryNames = topCommittedRepositories.map((data: any) => data.repositoryName);
                const commitCounts = topCommittedRepositories.map((data: any) => data.commits);
                setBarChartLabels(repositoryNames);
                setBarChartValues(commitCounts);
            }
        }
        fetchDashboardData()
    }, [userDataStore.token])
    return (
        <>
            <Container className="mt-3">
                <div className="d-flex flex-row justify-content-between py-3">
                    <h1>Dashboard</h1>
                </div>
                <Row>
                    <Col>
                        <Card className="mr-2">
                            <Card.Body>
                                <Card.Title>Total Repositories</Card.Title>
                                <Card.Text>{dashboardData.totalRepositories}</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title>Total Ownership</Card.Title>
                                <Card.Text>{dashboardData.totalProjectOwnership}</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title>Total Contributions</Card.Title>
                                <Card.Text>{dashboardData.totalProjectContributions}</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title>Total Followers</Card.Title>
                                <Card.Text>{dashboardData.numberOfFollowers}</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>

                <Row className="mt-4">
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title>Total Contributed Repositories</Card.Title>
                                <BarChart label="Repository" labels={barChartLabels} data={barChartValues} />
                            </Card.Body>
                        </Card>
                    </Col>
                    {/* <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title>Chart 2</Card.Title>
                            </Card.Body>
                        </Card>
                    </Col> */}
                </Row>
            </Container>
        </>
    )
}
export default Dashboard;