import React, { useState } from 'react';
import Modal from '@mui/material/Modal';
import axios from 'axios';

import { useUserStore } from '../store/userStore';

function ModalView({ isOpen, closeModal, refreshUserList }) {
  const {
    formData,
    handleChange,
    resetFormData,
    submitUserForm,
  } = useUserStore();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await submitUserForm();
      alert('사용자 등록 완료');
      closeModal(); // 등록 후 닫기
      refreshUserList(); // ✅ 상위에서 목록 새로고침
    } catch (err) {
      alert('등록 실패');
      console.error(err);
    }
  };

  return (
    <Modal open={isOpen} onClose={closeModal}>
      <div className="fixed inset-0 flex items-center justify-center z-50">
        <div className="relative max-w-md w-full bg-white p-6 rounded-lg shadow-lg">

          <h2 className="text-xl font-bold mb-4 text-center">사용자 등록</h2>
          <form onSubmit={handleSubmit}>
            <table className="w-full table-fixed border-spacing-y-2 border-separate">
              <tbody>
                <tr>
                  <td className="font-medium w-32">ID</td>
                  <td>
                    <input
                      type="text"
                      name="userId"
                      value={formData.userId}
                      onChange={handleChange}
                      className="w-full px-3 py-2 border rounded-md"
                      required
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
                      required
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
                      required
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
              {/* 등록 버튼 */}
              <button
                type="submit"
                className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
              >
                등록
              </button>

              {/* 닫기 버튼 */}
              <button
                type="button"
                onClick={closeModal}
                className="bg-gray-300 text-gray-800 px-4 py-2 rounded-md hover:bg-gray-400"
              >
                닫기
              </button>
            </div>
          </form>
        </div>
      </div>
    </Modal>
  );
}

export default ModalView;