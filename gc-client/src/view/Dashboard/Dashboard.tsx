import { Container } from "react-bootstrap";
import DashboardCard from "../../components/ui/Dashboard/Card";

const Dashboard = () => {
    return (
        <>
            <Container className="mt-3">
                <div className="d-flex flex-row justify-content-between py-3">
                    <h1>Dashboard</h1>
                </div>
                <div className="d-flex flex-wrap">
                    <DashboardCard title="Total PR" value="25"/>
                    <DashboardCard title="Total PR" value="25"/>
                    <DashboardCard title="Total PR" value="25"/>
                </div>
                <div>
                    <h1>Chart1</h1>
                    <h1>Chart2</h1>
                </div>
            </Container>
        </>
    )
}
export default Dashboard;