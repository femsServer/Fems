import React, { useEffect, useState } from 'react';
import axios from 'axios';

function UserGrid() {
  const [userData, setUserData] = useState([]);
  const [selectedId, setSelectedId] = useState(null);

  // ✅ 사용자 목록 조회 함수
  const fetchUserList = () => {
    axios.get('/api/user/list')
      .then((res) => setUserData(res.data))
      .catch((err) => console.error('데이터 조회 실패:', err));
  };

  // ✅ 삭제 함수 (컴포넌트 내부에서 정의)
  const deleteUser = async () => {
    if (!selectedId) {
      alert('삭제할 사용자를 선택하세요.');
      return;
    }

    try {
      const response = await axios.post('/api/user/delete', { userId: selectedId });
      console.log('삭제 성공:', response.data);
      fetchUserList(); // 👉 목록 갱신
      setSelectedId(null); // 👉 선택 해제
      alert('삭제되었습니다.');
    } catch (error) {
      console.error('삭제 실패:', error);
      alert('삭제 중 오류가 발생했습니다.');
    }
  };

  // 최초 로딩 시 사용자 목록 조회
  useEffect(() => {
    fetchUserList();
  }, []);

  const handleSelect = (id) => {
    setSelectedId(id);
  };

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full table-auto border border-gray-300">
        <thead className="bg-gray-100">
          <tr>
            <th className="border px-4 py-2">선택</th>
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
              onClick={() => handleSelect(user.userId)}
            >
              <td className="border px-4 py-2 text-center">
                <input
                  type="radio"
                  name="userSelect"
                  checked={selectedId === user.userId}
                  onChange={() => handleSelect(user.userId)}
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

      <button
        onClick={deleteUser}
        className="mt-4 bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700"
      >
        삭제
      </button>
    </div>
  );
}

export default UserGrid;
