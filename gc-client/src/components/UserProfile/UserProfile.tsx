import React, { useState } from 'react';
import { Button, Card, Image } from 'react-bootstrap';
import { FaLinkedin, FaGithub, FaCloud } from 'react-icons/fa';
import ProfileEditModal from './ProfileEditModal/ProfileEditModal';
import { store } from '../../store/store';
import { selectUserData } from '../../redux/userSlice';


interface UserProfileProps {
  username: string;
  followers: number;
  following: number;
  stars: number;
  organization: string;
  location: string;
  description: string;
  linkedin: string;
  github: string;
  resume: string;
  profilePicture: string;
  //skills: string[];
}

const UserProfile: React.FC<UserProfileProps> = ({
  username,
  followers,
  following,
  stars,
  organization,
  location,
  description,
  linkedin,
  github,
  resume,
  profilePicture,
  //skills,
}) => {
  const [showEditModal, setShowEditModal] = useState(false);

  const handleEditProfile = () => {
    setShowEditModal(true);
  };

  const handleModalClose = () => {
    setShowEditModal(false);
  };

  const rdx_store = store;
  const userData = selectUserData(rdx_store.getState().userData!)

  //console.log("redux store user data: ", userData)
  //console.log("redux store user data profile picture: ", userData["profilePicture"])
 
  return (
    <Card className="user-profile" style={{width:'40em', marginRight:'10em'}}>
    
      <Image src={userData["profilePicture"]} alt="Profile Picture" 
              style={{maxWidth: '100%', height: '30em'}}/>
      <Card.Body>

        <Card.Title style={{"paddingBottom":10}}>
          {/*displayname*/}
          {userData["username"]}
        </Card.Title>
        {/*
        <Card.Subtitle>
        username
        {currentUser["username"]}
        </Card.Subtitle>
        -> Followers, Following, Stars need to come from github API
        */}
        <Card.Text>Followers: {followers}</Card.Text>
        <Card.Text>Following: {following}</Card.Text>
        <Card.Text>Stars: {stars}</Card.Text>
        <Card.Text>Organization: {userData["organization"]}</Card.Text>
        <Card.Text>Location: {userData["location"]}</Card.Text>
        <Card.Text>Description: {userData["description"]}</Card.Text>
        <Card.Text>
          LinkedIn:{' '}
          <a href={userData['linkedin']} target="_blank" rel="noopener noreferrer">
            <FaLinkedin />
          </a>
        </Card.Text>
        <Card.Text>
          GitHub:{' '}
          <a href={userData['github']} target="_blank" rel="noopener noreferrer">
            <FaGithub />
          </a>
        </Card.Text>
        <Card.Text>
                  Resume:{' '}
                  <a href={userData['resume']} target="_blank" rel="noopener noreferrer">
                    <FaCloud />
                  </a>
                </Card.Text>
        {/* 
        <Card.Text>Skills:</Card.Text>
        --- Removed Skills as it is not needed since users can upload resume
        <div className="skills">
          {skills.map((skill) => (
            <Button key={skill} variant="secondary" className="skill-badge">
              {skill}
            </Button>
          ))}
        </div>
        */}
        <Button variant="primary" onClick={handleEditProfile} style={{marginTop:'0.5em'}}>
          Edit Profile
        </Button>
      </Card.Body>
      {showEditModal && (
        <ProfileEditModal
          userProfile={{
            username,
            organization,
            location,
            description,
            linkedin,
            github,
            resume,
            profilePicture,
            //skills,
          }}
          handleClose={handleModalClose}
        />
      )}
    </Card>
  );
};

export default UserProfile;
