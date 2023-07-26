import React, { useEffect, useState } from 'react';
import { Button, Card, Image } from 'react-bootstrap';
import { FaLinkedin, FaGithub, FaCloud } from 'react-icons/fa';
import ProfileEditModal from './ProfileEditModal/ProfileEditModal';
import { useSelector } from 'react-redux';
import { selectUser } from '../../redux/userSlice';
import { getUserData } from '../../services/UserService';


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
  const [userData, setUserData] = useState({} as any);
  const userDataStore = useSelector(selectUser);

  const handleEditProfile = () => {
    setShowEditModal(true);
  };

  const handleModalClose = () => {
    setShowEditModal(false);
  };

  useEffect(() => {
    async function fetchUserData() {
      let response = await getUserData(userDataStore.username, userDataStore.token);
      if(response?.status === 200) {
        setUserData(response?.data?.body);
      }
    }
    fetchUserData()
  }, [userDataStore.token, userDataStore.username])

  return (
    <Card className="user-profile" style={{ width: '40em', marginRight: '10em' }}>

      <Image src={userData.profilePicture ? userData.profilePicture : 'https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'} alt="Profile Picture"
        style={{ maxWidth: '100%', height: '45em' }} />
      <Card.Body>

        <Card.Title style={{ "paddingBottom": 10 }}>
          {userData.firstName} {userData.lastName}
        </Card.Title>
        <Card.Text>Followers: {followers}</Card.Text>
        <Card.Text>Following: {following}</Card.Text>
        <Card.Text>Stars: {stars}</Card.Text>
        <Card.Text>Organization: {userData.organization}</Card.Text>
        <Card.Text>Location: {userData.location}</Card.Text>
        <Card.Text>Description: {userData.description}</Card.Text>
        <Card.Text>
          LinkedIn:{' '}
          <a href={userData.linkedin} target="_blank" rel="noopener noreferrer">
            <FaLinkedin />
          </a>
        </Card.Text>
        <Card.Text>
          GitHub:{' '}
          <a href={userData.github} target="_blank" rel="noopener noreferrer">
            <FaGithub />
          </a>
        </Card.Text>
        <Card.Text>
          Resume:{' '}
          <a href={userData.resume} target="_blank" rel="noopener noreferrer">
            <FaCloud />
          </a>
        </Card.Text>
        <Button variant="primary" onClick={handleEditProfile} style={{ marginTop: '0.5em' }}>
          Edit Profile
        </Button>
      </Card.Body>
      {showEditModal && (
        <ProfileEditModal
          userProfile={{
            ...userData
          }}
          handleClose={handleModalClose}
        />
      )}
    </Card>
  );
};

export default UserProfile;
