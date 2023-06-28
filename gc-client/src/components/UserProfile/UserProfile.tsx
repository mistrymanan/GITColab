import React, { useState } from 'react';
import { Button, Card, Image, Badge } from 'react-bootstrap';
import { FaLinkedin, FaGithub, FaCloud } from 'react-icons/fa';
import ProfileEditModal from './ProfileEditModal/ProfileEditModal';

interface UserProfileProps {
displayname: string;
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
  skills: string[];
}

const UserProfile: React.FC<UserProfileProps> = ({
displayname,
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
  skills,
}) => {
  const [showEditModal, setShowEditModal] = useState(false);

  const handleEditProfile = () => {
    setShowEditModal(true);
  };

  const handleModalClose = () => {
    setShowEditModal(false);
  };

  return (
    <Card className="user-profile">

      <Image src="https://blog.linkedin.com/content/dam/blog/en-us/corporate/blog/2014/07/Anais_Saint-Jude_L4388_SQ.jpg.jpeg" alt="Profile Picture" rounded className="profile-picture" />
      <Card.Body>

        <Card.Title>
          {displayname}
        </Card.Title>
        <Card.Subtitle>
        {username}
        </Card.Subtitle>
        <Card.Text>Followers: {followers}</Card.Text>
        <Card.Text>Following: {following}</Card.Text>
        <Card.Text>Stars: {stars}</Card.Text>
        <Card.Text>Organization: {organization}</Card.Text>
        <Card.Text>Location: {location}</Card.Text>
        <Card.Text>Description: {description}</Card.Text>
        <Card.Text>
          LinkedIn:{' '}
          <a href={linkedin} target="_blank" rel="noopener noreferrer">
            <FaLinkedin />
          </a>
        </Card.Text>
        <Card.Text>
          GitHub:{' '}
          <a href={github} target="_blank" rel="noopener noreferrer">
            <FaGithub />
          </a>
        </Card.Text>
        <Card.Text>
                  Resume:{' '}
                  <a href={resume} target="_blank" rel="noopener noreferrer">
                    <FaCloud />
                  </a>
                </Card.Text>
        <Card.Text>Skills:</Card.Text>
        <div className="skills">
          {skills.map((skill) => (
            <Button key={skill} variant="secondary" className="skill-badge">
              {skill}
            </Button>
          ))}
        </div>
        <Button variant="primary" onClick={handleEditProfile}>
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
            skills,
          }}
          handleClose={handleModalClose}
        />
      )}
    </Card>
  );
};

export default UserProfile;
