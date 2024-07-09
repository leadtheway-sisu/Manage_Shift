/**
 * index.js
 */

document.addEventListener('DOMContentLoaded', function() {
  displayShiftTable();
});

async function displayShiftTable() {
  try {
    const response = await fetch('/api/all-shifts');
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const shifts = await response.json();

    const employees = Array.from(new Set(shifts.map(shift => shift.username)));
    const today = new Date();
    const nextMonth = new Date(today.getFullYear(), today.getMonth() + 1, 1);
    const lastDay = new Date(nextMonth.getFullYear(), nextMonth.getMonth() + 1, 0).getDate();

    const dates = [];
    for (let i = 1; i <= lastDay; i++) {
      const dateString = `${nextMonth.getFullYear()}-${(nextMonth.getMonth() + 1).toString().padStart(2, '0')}-${i.toString().padStart(2, '0')}`;
      dates.push(dateString);
    }

    const tableBody = document.querySelector('#table-body');
    const tableHeader = document.querySelector('#shift-table thead tr');

    // 日付と曜日をヘッダーに追加
    dates.forEach(date => {
      const headerCell = document.createElement('th');
      const dateObj = new Date(date);
      const formattedDate = `${dateObj.getMonth() + 1}/${dateObj.getDate()}`;
      const dayOfWeek = getDayOfWeek(dateObj.getDay());
      headerCell.textContent = `${formattedDate} (${dayOfWeek})`;
      tableHeader.appendChild(headerCell);
    });

    // 従業員ごとに行を追加
    employees.forEach(employee => {
      const row = document.createElement('tr');
      const nameCell = document.createElement('td');
      nameCell.textContent = employee;
      row.appendChild(nameCell);

      dates.forEach(date => {
        const dateCell = document.createElement('td');
        const matchingShift = shifts.find(shift => shift.username === employee && shift.selectedDate === date);
        if (matchingShift) {
          // マッチするシフトデータがある場合
          const shiftType = matchingShift.shiftType;
          const textNode = document.createTextNode(shiftType);
          dateCell.appendChild(textNode);
        } else {
          // マッチするシフトデータがない場合
          const textNode = document.createTextNode('-');
          dateCell.appendChild(textNode);
        }
        row.appendChild(dateCell);
      });

      tableBody.appendChild(row);
    });
  } catch (error) {
    console.error('Error fetching data:', error);
  }
}

function getDayOfWeek(dayIndex) {
  const daysOfWeek = ['日', '月', '火', '水', '木', '金', '土'];
  return daysOfWeek[dayIndex];
}

