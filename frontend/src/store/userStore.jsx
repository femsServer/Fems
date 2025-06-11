import { useState, useCallback } from 'react';

import { fetchUsers, deleteUsers, createUser } from '../api/userApi';

export function useUserStore() {
  const [userData, setUserData] = useState([]);
  const [selectedId, setSelectedId] = useState(null);
  const [selectedIds, setSelectedIds] = useState([]);

  // ✅ 사용자 목록 조회 함수
  const fetchUserList = useCallback(() => {
    fetchUsers()
      .then(res => setUserData(res.data))
      .catch(err => console.error('데이터 조회 실패:', err));
  }, []);

  // ✅ 삭제 함수 (컴포넌트 내부에서 정의)
  const deleteUser = useCallback(async () => {
    if (selectedIds.length === 0) {
      alert('삭제할 사용자를 선택하세요.');
      return;
    }

    const confirmed = window.confirm('사용자를 삭제하시겠습니까?');
    if (!confirmed) {
        return; // 사용자가 취소했으면 함수 종료
    }

    try {
      await deleteUsers(selectedIds);
      alert('삭제되었습니다.');
      fetchUserList();
      clearSelection();
    } catch (error) {
      console.error('삭제 실패:', error);
      alert('삭제 중 오류가 발생했습니다.');
    }
  }, [selectedIds, fetchUserList]);

  // ✅ 사용자 등록 함수
  // 사용자 등록 폼 상태
  const [formData, setFormData] = useState({
    userId: '',
    userPw: '',
    userName: '',
    phone: '',
    email: '',
    roleLevel: '',
    useYn: 'Y',
  });

  // 폼 입력 변경 핸들러
  const handleChange = useCallback((e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'useYn' ? (checked ? 'Y' : 'N') : value,
    }));
  }, []);

  // 폼 초기화
  const resetFormData = useCallback(() => {
    setFormData({
      userId: '',
      userPw: '',
      userName: '',
      phone: '',
      email: '',
      roleLevel: '',
      useYn: 'Y',
    });
  }, []);

  // 사용자 등록 API 호출
  const submitUserForm = useCallback(async () => {
    await createUser(formData);
    resetFormData(); // 폼 초기화
  }, [formData, resetFormData]);


  // ✅ table checkbox 선택 관리
  const selectUser = useCallback((id) => setSelectedId(id), []);
  const toggleSelectUser = useCallback((id) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((v) => v !== id) : [...prev, id]
    );
  }, []);
  const toggleSelectAll = useCallback(() => {
    if (selectedIds.length === userData.length) {
      setSelectedIds([]);
    } else {
      setSelectedIds(userData.map((u) => u.userId));
    }
  }, [userData, selectedIds]);

  const clearSelection = useCallback(() => {
    setSelectedId(null);
    setSelectedIds([]);
  }, []);

  return {
    // 사용자 목록 관련
    userData,
    selectedId,
    selectedIds,
    fetchUserList,
    deleteUser,
    selectUser,
    toggleSelectUser,
    toggleSelectAll,
    clearSelection,

    // 사용자 등록 관련
    formData,
    handleChange,
    resetFormData,
    submitUserForm,
    setFormData,
  };
}