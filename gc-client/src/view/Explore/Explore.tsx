import { useState } from "react";
import { Container, Dropdown } from "react-bootstrap";
import CardComponent from "../../components/ui/Card";

const Explore = () => {
    const [showAddProjectFlow, setShowSearchProjectFlow] = useState(false);

    const searchProjectFlowHandler = () => {
        setShowSearchProjectFlow(true);
    };

    return (
        <>
            <Container className="mt-3">
                <div className="d-flex flex-row my-2 justify-content-between">
                    <h1 className='py-3'> Explore New Projects </h1>

                    <Dropdown onClick={searchProjectFlowHandler}>
                        <Dropdown.Toggle variant="dark" id="dropdown-basic">
                            Search Project
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            <Dropdown.Item href="#/action-1">1st Connection</Dropdown.Item>
                            <Dropdown.Item href="#/action-2">2nd Connection</Dropdown.Item>
                            <Dropdown.Item href="#/action-3">3rd Connection</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                </div>
                
                <div className="d-flex flex-wrap justify-content-between my-2">
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                    <CardComponent />
                </div>
            </Container>
        </>
    )
}

export default Explore;
