import React, { useState, useEffect } from 'react';
import Modal from '@mui/material/Modal';
import axios from 'axios';

function EditUserModal({ isOpen, closeModal, selectedUser, refreshUserList }) {
  const [formData, setFormData] = useState({
    userId: '',
    userPw: '',
    userName: '',
    phone: '',
    email: '',
    roleLevel: '',
    useYn: true,
  });

  // 선택된 사용자 데이터로 폼 초기화
  useEffect(() => {
    if (selectedUser) {
      setFormData({
        ...selectedUser,
        useYn: selectedUser.useYn === 'Y' || selectedUser.useYn === true,
      });
    }
  }, [selectedUser]);

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
      const updatedData = {
        ...formData,
        useYn: formData.useYn ? 'Y' : 'N',
      };

      await axios.put(`/api/user/${formData.userId}`, updatedData);
      alert('사용자 수정 완료');
      closeModal();
      refreshUserList();
    } catch (err) {
      alert('수정 실패');
      console.error(err);
    }
  };

  return (
    <Modal open={isOpen} onClose={closeModal}>
      <div className="fixed inset-0 flex items-center justify-center z-50">
        <div className="relative max-w-md w-full bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-bold mb-4 text-center">사용자 수정</h2>
          <form onSubmit={handleSubmit}>
            <table className="w-full border-spacing-y-2 border-separate">
              <tbody>
                <tr>
                  <td className="font-medium w-32">ID</td>
                  <td>
                    <input
                      type="text"
                      name="userId"
                      value={formData.userId}
                      disabled
                      className="w-full px-3 py-2 border rounded-md bg-gray-100"
                    />
                  </td>
                </tr>
                <tr>
                  <td className="font-medium">Password</td>
                  <td>
                    <input
                      type="password"
                      name="userPw"
                      value={formData.userPw}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border rounded-md"
                    />
                  </td>
                </tr>
                <tr>
                  <td className="font-medium">이름</td>
                  <td>
                    <input
                      type="text"
                      name="userName"
                      value={formData.userName}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border rounded-md"
                    />
                  </td>
                </tr>
                <tr>
                  <td className="font-medium">전화번호</td>
                  <td>
                    <input
                      type="tel"
                      name="phone"
                      value={formData.phone}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border rounded-md"
                    />
                  </td>
                </tr>
                <tr>
                  <td className="font-medium">이메일</td>
                  <td>
                    <input
                      type="email"
                      name="email"
                      value={formData.email}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border rounded-md"
                    />
                  </td>
                </tr>
                <tr>
                  <td className="font-medium">권한 레벨</td>
                  <td>
                    <input
                      type="number"
                      name="roleLevel"
                      value={formData.roleLevel}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border rounded-md"
                    />
                  </td>
                </tr>
                <tr>
                  <td className="font-medium">사용여부</td>
                  <td className="flex items-center gap-2">
                    <input
                      type="checkbox"
                      name="useYn"
                      checked={formData.useYn}
                      onChange={handleChange}
                    />
                    <span>{formData.useYn ? 'Y' : 'N'}</span>
                  </td>
                </tr>
              </tbody>
            </table>

            <div className="mt-6 flex justify-end gap-x-2">
              <button
                type="submit"
                className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700"
              >
                저장
              </button>
              <button
                type="button"
                onClick={closeModal}
                className="bg-gray-300 text-gray-800 px-4 py-2 rounded-md hover:bg-gray-400"
              >
                취소
              </button>
            </div>
          </form>
        </div>
      </div>
    </Modal>
  );
}

export default EditUserModal;