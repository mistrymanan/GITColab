import React, { useState } from 'react';
import { Modal, Button, Form, Dropdown } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { updateUserProfile, getUserData } from '../../../services/UserService';
import { store } from '../../../store/store';
import jwt from 'jwt-decode';
import { updateProfile } from "../../../redux/userSlice";

/*Start of baseer's code*/
interface ProfileEditModalProps {
  userProfile: UserProfileData;
  handleClose: () => void;
}

interface UserProfileData {
  username: string;
  organization: string;
  location: string;
  description: string;
  linkedin: string;
  github: string;
  resume: string;
  skills: string[];
}

const ProfileEditModal: React.FC<ProfileEditModalProps> = ({
  userProfile,
  handleClose,
}) => {
  const [formData, setFormData] = useState<UserProfileData>(userProfile);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleSkillsChange = (selectedSkills: string[]) => {
    setFormData((prevFormData) => ({
      ...prevFormData,
      skills: selectedSkills,
    }));
  };
  
  const dispatch = useDispatch();
  const rdx_store = store;
  /*End of baseer's code*/

    
    /*
    my code per keyur's request but commented out for testing
    //State handlers
    const [username, setUsername] = useState('');
    const [organization, setOrganization] = useState('');
    const [location, setLocation] = useState('');
    const [description, setDescription] = useState('');
    const [linkedin, setLinkedin] = useState('');
    const [github, setGithub] = useState('');
    const [resume, setResume] = useState('');
    const dispatch = useDispatch();
  */

  const handleSubmit = async (event: any) => {
    // Perform the profile update logic here, e.g., make an API request
    const form = event.currentTarget;
    event.preventDefault();

    if(form.checkValidity() === true){
      const currentUser = JSON.parse(localStorage.getItem('userProfile') || '{}');
      const data = {username : currentUser["username"], organization: formData.organization, location: 
                    formData.location, description: formData.description, linkedin: formData.linkedin, 
                    github: formData.github, resume: formData.resume}
  
      const response = await updateUserProfile(data);

      if(response != null){
        dispatch(
          updateProfile({
              userData: data
          })
      )
      }

      //decrypt current token, send get request to get newly updated fields, update state of store  -> not neede
      
    }

    
    handleClose();
  };

  return (
    <Modal show={true} onHide={handleClose}>
      
      <Modal.Header closeButton>
        <Modal.Title>Edit Profile</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          {/* user's will not be allowed to change their usernames
          
          <Form.Group controlId="username">
            <Form.Label>Username</Form.Label>
            <Form.Control
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
            />
            
          </Form.Group>
          */}
          <Form.Group controlId="organization">
            <Form.Label>Organization</Form.Label>
            <Form.Control
              type="text"
              name="organization"
              placeholder='Enter your Organization'
              value={formData.organization}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="location">
            <Form.Label>Location</Form.Label>
            <Form.Control
              type="text"
              name="location"
              placeholder='Enter your Location'
              value={formData.location}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="description">
            <Form.Label>Description</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              name="description"
              placeholder='Enter a Description'
              value={formData.description}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="linkedin">
            <Form.Label>LinkedIn</Form.Label>
            <Form.Control
              type="text"
              name="linkedin"
              placeholder='Enter your LinkedIn Profile Link'
              value={formData.linkedin}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="github">
            <Form.Label>GitHub</Form.Label>
            <Form.Control
              type="text"
              name="github"
              placeholder='Enter your Github Profile Link'
              value={formData.github}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="resume">
            <Form.Label>Resume</Form.Label>
            <Form.Control
              type="text"
              name="resume"
              value={formData.resume}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="skills">
            <Form.Label>Skills</Form.Label>
            <Dropdown>
              <Dropdown.Toggle variant="secondary" id="dropdown-basic">
                Select Skills
              </Dropdown.Toggle>
              <Dropdown.Menu>
                <Dropdown.Item
                  onClick={() => handleSkillsChange([...formData.skills, 'HTML'])}
                >
                  HTML
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => handleSkillsChange([...formData.skills, 'CSS'])}
                >
                  CSS
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => handleSkillsChange([...formData.skills, 'JavaScript'])}
                >
                  JavaScript
                </Dropdown.Item>
                {/* Add more dropdown items for other skills */}
              </Dropdown.Menu>
            </Dropdown>
            {formData.skills.map((skill) => (
              <span key={skill} className="skill-button">

                    
              </span>
            ))}
          </Form.Group>
          <Button variant="primary" type="submit">
            Save Changes
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ProfileEditModal;
