import React, { useEffect, useState } from 'react';
import axios from 'axios';

const EmployerProfile = () => {
    const [employer, setEmployer] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user?.userId; // Use optional chaining to avoid errors if user is null

    useEffect(() => {
        if (userId) {
            axios.get(`http://localhost:8080/api/employers/GetByUserId/${userId}`)
                .then(response => {
                    setEmployer(response.data);
                    setLoading(false);
                })
                .catch(error => {
                    setError('There was an error fetching the employer data!');
                    setLoading(false);
                });
        } else {
            setError('UserId not found in local storage');
            setLoading(false);
        }
    }, [userId]);

    const styles = {
        container: {
            fontFamily: 'Arial, sans-serif',
            padding: '20px',
            maxWidth: '800px',
            margin: '40px auto', // Added top and bottom margin
            border: '1px solid #ddd',
            borderRadius: '8px',
            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
            backgroundColor: '#f9f9f9'
        },
        header: {
            fontSize: '2rem',
            marginBottom: '10px',
            color: '#333'
        },
        section: {
            marginBottom: '20px'
        },
        sectionTitle: {
            fontSize: '1.5rem',
            marginBottom: '5px',
            color: '#555'
        },
        paragraph: {
            fontSize: '1rem',
            lineHeight: '1.5',
            color: '#666'
        },
        error: {
            color: 'red',
            fontWeight: 'bold'
        },
        loading: {
            fontStyle: 'italic',
            color: '#888'
        }
    };

    if (loading) {
        return <div style={styles.loading}>Loading...</div>;
    }

    if (error) {
        return <div style={styles.error}>Error: {error}</div>;
    }

    if (!employer) {
        return <div>No employer data available.</div>;
    }

    return (
        <div style={styles.container}>
            <h1 style={styles.header}>Employer Profile</h1>
            <div style={styles.section}>
                <h2 style={styles.sectionTitle}>Company Name</h2>
                <p style={styles.paragraph}>{employer.companyName}</p>
            </div>
            <div style={styles.section}>
                <h2 style={styles.sectionTitle}>Description</h2>
                <p style={styles.paragraph}>{employer.companyDescription}</p>
                <p style={styles.paragraph}><strong>Created At:</strong> {new Date(employer.createdAt).toLocaleDateString()}</p>
            </div>
            <div style={styles.section}>
                <h2 style={styles.sectionTitle}>Contact Information</h2>
                <p style={styles.paragraph}><strong>Email:</strong> {user.email}</p>
            </div>
        </div>
    );
};

export default EmployerProfile;
