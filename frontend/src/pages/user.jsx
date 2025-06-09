import React, { useState } from 'react';
import axios from 'axios';


function UserRegistrationForm() {
  const [formData, setFormData] = useState({
    userId: '',
    userPw: '',
    userName: '',
    phone: '',
    email: '',
    roleLevel: '',
    useYn: 'Y',
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/user/register', formData);
      console.log('서버 응답:', response.data);
      alert('사용자 등록 완료');
    } catch (error) {
      console.error('등록 오류:', error);
      alert('등록 중 오류 발생');
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 border rounded-lg shadow-md bg-white">
      <h2 className="text-2xl font-bold mb-6 text-center">사용자 등록</h2>
      <form onSubmit={handleSubmit}>
        {/** ID */}
        <div className="mb-4">
          <label className="block font-medium mb-1">ID</label>
          <input
            type="text"
            name="userId"
            value={formData.id}
            onChange={handleChange}
            required
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        {/** Password */}
        <div className="mb-4">
          <label className="block font-medium mb-1">Password</label>
          <input
            type="password"
            name="userPw"
            value={formData.password}
            onChange={handleChange}
            required
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        {/** Username */}
        <div className="mb-4">
          <label className="block font-medium mb-1">Username</label>
          <input
            type="text"
            name="userName"
            value={formData.username}
            onChange={handleChange}
            required
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        {/** Phone */}
        <div className="mb-4">
          <label className="block font-medium mb-1">Phone</label>
          <input
            type="tel"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        {/** Email */}
        <div className="mb-4">
          <label className="block font-medium mb-1">Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        {/** Role Level */}
        <div className="mb-4">
          <label className="block font-medium mb-1">Role Level</label>
          <input
            type="number"
            name="roleLevel"
            value={formData.role_level}
            onChange={handleChange}
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        {/** Is Active */}
        <div className="mb-6 flex items-center">
          <input
            type="checkbox"
            name="userYn"
            checked={formData.is_active}
            onChange={handleChange}
            className="mr-2"
          />
          <label className="font-medium">활성화 여부 (is_active)</label>
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700"
        >
          등록
        </button>
      </form>
    </div>
  

  );
}

export default UserRegistrationForm;
