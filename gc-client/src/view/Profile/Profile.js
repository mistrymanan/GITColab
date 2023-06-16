import mockData from '../../model/Mockdata'
import {Col, Row} from 'react-bootstrap';
import React from 'react';
import Project from '../../components/Project/Project';

const Profile = () => {
    return (
        <>
            <h1 className='py-3'>Projects</h1>
            {/*Loop through the mockdata and display each on a card*/}
            <Row >
                {mockData.map( (data) => (
                    <Col sm = {12} md={6} lg = {4}>
                       
                        <Project data={data}/>
                    </Col>
                ))}
            </Row>
        </>
    )
}

export default Profile;