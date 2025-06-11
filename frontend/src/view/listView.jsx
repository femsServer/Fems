import React, { useEffect, useState } from 'react';
import { Button } from '@mui/material';

import { useUserStore } from '../store/userStore';
import ModalVeiw from './modalView';
import EditUserModal from './EditUserModal';

function listView() {
  const {
    userData,
    selectedId,
    selectedIds,
    fetchUserList,
    deleteUser,
    selectUser,
    toggleSelectUser,
    toggleSelectAll,
  } = useUserStore();

  // modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEditOpen, setIsEditOpen] = useState(false);

  // 최초 로딩 시 사용자 목록 조회
  useEffect(() => {
    fetchUserList();
  }, [fetchUserList]);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  const openEditModal = () => setIsEditOpen(true);
  const closeEditModal = () => setIsEditOpen(false);

  // table checkbox 전체 선택
  const isAllSelected = userData.length > 0 && selectedIds.length === userData.length;

  const selectedRowData = userData.find(user => user.userId === selectedId);

  return (
    <div className="overflow-x-auto">
      <div style={{ display: "flex", justifyContent: "flex-end", gap: "10px", marginBottom: "20px" }}>
        {/* 등록 버튼 */}
        <Button color="primary" variant="contained" onClick={openModal}>등록</Button>

        {/* 수정 버튼 */}
        <Button
          color="primary"
          variant="contained"
          onClick={() => {
            if (!selectedRowData) {
              alert('수정할 사용자를 선택하세요.');
              return;
            }
            openEditModal();
          }}
        >
          수정
        </Button>

        {/* 삭제 버튼 */}
        <Button
          color="error"
          variant="contained"
          onClick={deleteUser}>
          삭제
        </Button>
      </div>

      <ModalVeiw
        isOpen={isModalOpen}
        closeModal={closeModal}
        refreshUserList={fetchUserList} // 새로고침 함수 전달
      />

      <EditUserModal
        isOpen={isEditOpen}
        closeModal={closeEditModal}
        selectedUser={selectedRowData}
        refreshUserList={fetchUserList}
      />

      <table className="min-w-full table-auto border border-gray-300">
        <thead className="bg-gray-100">
          <tr>
            <th className="border px-4 py-2 text-center">
              <input
                type="checkbox"
                checked={isAllSelected}
                onChange={toggleSelectAll}
              />
            </th>
            <th className="border px-4 py-2">ID</th>
            <th className="border px-4 py-2">이름</th>
            <th className="border px-4 py-2">이메일</th>
            <th className="border px-4 py-2">연락처</th>
          </tr>
        </thead>
        <tbody>
          {userData.map((user) => (
            <tr
              key={user.userId}
              className={`cursor-pointer ${selectedId === user.userId ? 'bg-blue-100' : ''}`}
              onClick={() => selectUser(user.userId)}
            >
              <td className="border px-4 py-2 text-center">
                <input
                  type="checkbox"
                  checked={selectedIds.includes(user.userId)}
                  onChange={() => toggleSelectUser(user.userId)}
                />
              </td>
              <td className="border px-4 py-2">{user.userId}</td>
              <td className="border px-4 py-2">{user.userName}</td>
              <td className="border px-4 py-2">{user.email}</td>
              <td className="border px-4 py-2">{user.phone}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default listView;