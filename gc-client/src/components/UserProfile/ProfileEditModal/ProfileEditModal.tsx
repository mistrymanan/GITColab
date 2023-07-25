import React, { useState } from 'react';
import { Modal, Button, Form, Dropdown } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { updateUserProfile } from '../../../services/UserService';
import { store } from '../../../store/store';
import { updateProfile, selectUserData} from "../../../redux/userSlice";


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
  profilePicture: string;
  //skills: string[];
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

  /*
  const handleSkillsChange = (selectedSkills: string[]) => {
    setFormData((prevFormData) => ({
      ...prevFormData,
      skills: selectedSkills,
    }));
  };

  */
  /*End of baseer's code*/

  const dispatch = useDispatch();
  const rdx_store = store;
  const userData = selectUserData(rdx_store.getState().userData!)
    
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

      console.log(userData);

      //If any field of the form is empty, populate the form data with the current state of that field
      //That way, the front-end only changes when fields are populated by the user.
      if(formData.profilePicture.length < 1){
        formData.profilePicture = userData["profilePicture"];
      }

      if(formData.organization.length < 1){
        formData.organization = userData["organization"];
      }

      if(formData.location.length < 1){
        formData.location = userData["location"];
      }

      if(formData.description.length < 1){
        formData.description = userData["description"];
      }

      if(formData.linkedin.length < 1){
        formData.linkedin = userData["linkedin"];
      }

      if(formData.github.length < 1){
        formData.github = userData["github"];
      }

      if(formData.resume.length < 1){
        formData.resume = userData["resume"];
      }
      
      //preparing profile data to be updated 
      const data = {username : userData["username"], organization: formData.organization, location: 
                    formData.location, description: formData.description, linkedin: formData.linkedin, 
                    github: formData.github, resume: formData.resume, profilePicture: formData.profilePicture}
  
      const response = await updateUserProfile(data);

      if(response != null){
        dispatch(
          updateProfile({
              userData: data
          })
      )
      }
      console.log(userData);
      
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
              placeholder='Enter Your Organization'
              value={formData.organization}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="location">
            <Form.Label>Location</Form.Label>
            <Form.Control
              type="text"
              name="location"
              placeholder='Enter Your Location'
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
              placeholder='Enter Profile Description'
              value={formData.description}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="linkedin">
            <Form.Label>LinkedIn</Form.Label>
            <Form.Control
              type="text"
              name="linkedin"
              placeholder='Enter Your LinkedIn Profile Link'
              value={formData.linkedin}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="github">
            <Form.Label>GitHub</Form.Label>
            <Form.Control
              type="text"
              name="github"
              placeholder='Enter Your Github Profile Link'
              value={formData.github}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="resume">
            <Form.Label>Resume</Form.Label>
            <Form.Control
              type="text"
              placeholder='Enter Your Resume Link'
              name="resume"
              value={formData.resume}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="profilePicture">
            <Form.Label>Profile Picture</Form.Label>
            <Form.Control
              type="text"
              placeholder='Enter Profile Picture Link'
              name="profilePicture"
              value={formData.profilePicture}
              onChange={handleChange}
            />
          </Form.Group>
          {/*--- Removed Skills as it is not needed since users can upload resume
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
               
              </Dropdown.Menu>
            </Dropdown>
            
            {formData.skills.map((skill) => (
              <span key={skill} className="skill-button">

                    
              </span>
            ))}
            
          </Form.Group>
          */}
          <Button variant="primary" type="submit" style={{marginTop:'2em'}}>
            Save Changes
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ProfileEditModal;
