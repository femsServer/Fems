import axios from 'axios';

// 사용자 목록 조회
export const fetchUsers = () => {
  return axios.get('/api/user');
};

// 사용자 등록
export const createUser = (userData) => {
  return axios.post('/api/user', userData);
};

// 사용자 수정
export const updateUser = (userId, userData) => {
  return axios.put(`/api/user/${userId}`, userData);
};

// 사용자 삭제 (여러 명 삭제 가능하도록 배열 받기)
export const deleteUsers = (userIds) => {
  // 서버 API가 배열을 받는지, 개별 요청 보내야 하는지 확인 필요
  // 예: 여러 개를 한 번에 삭제하는 API가 없다면 아래처럼 병렬 요청 실행
  return Promise.all(
    userIds.map(id => axios.delete('/api/user', { data: { userId: id } }))
  );
};