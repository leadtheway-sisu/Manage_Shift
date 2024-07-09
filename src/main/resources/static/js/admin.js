/**
 * admin.js
 */

document.addEventListener('DOMContentLoaded', function() {
    displayShiftTable();
    displayCompleteShift();
});

async function displayShiftTable() {
    try {
        const response = await fetch('/api/holiday-requests/all');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const holidayRequests = await response.json();
        const employees = Array.from(new Set(holidayRequests.map(request => request.username)));

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
                const matchingRequest = holidayRequests.find(request => request.username === employee && request.selectedDates === date);
                if (matchingRequest) {
                    // 既に休みとマークされている場合
                    const dropdown = createDropdown(['日','早','遅','夜','休','明'], '休');
                    dropdown.dataset.username = employee;
                    dropdown.dataset.date = date;
                    dateCell.appendChild(dropdown);
                } else {
                    // まだ休みとマークされていない場合
                    const dropdown = createDropdown(['日','早','遅','夜','休','明'], '日');
                    dropdown.dataset.username = employee;
                    dropdown.dataset.date = date;
                    dateCell.appendChild(dropdown);
                }
                row.appendChild(dateCell);
            });

            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

function createDropdown(options, defaultValue) {
    const select = document.createElement('select');
    options.forEach(option => {
        const optionElement = document.createElement('option');
        optionElement.textContent = option;
        optionElement.value = option;
        select.appendChild(optionElement);
    });
    select.value = defaultValue;
    return select;
}

async function displayCompleteShift() {　// 保存したシフトを表示させ確認可能にする。
    try {
        const response = await fetch('/api/all-shifts');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const shifts = await response.json();

        const dropdowns = document.querySelectorAll('select');
        dropdowns.forEach(dropdown => {
            const username = dropdown.dataset.username;
            const date = dropdown.dataset.date;

            // 対応するシフトデータを探す
            const matchingShift = shifts.find(shift => shift.username === username && shift.selectedDate === date);
            if (matchingShift) {
                dropdown.value = matchingShift.shiftType; // シフトタイプを設定
            }
        });

        // シフトデータを反映した後に必要な場合の処理を追加する

    } catch (error) {
        console.error('Error fetching shifts:', error);
    }
}


function completeShift(event) {
    event.preventDefault();

    const shifts = collectShiftData();

    getCsrfToken()
        .then(csrfToken => {
            // シフト情報を保存するためのPOSTリクエストを送信する
            fetch('/api/save-shifts', 
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + getAccessToken(),
                    'X-CSRF-TOKEN': csrfToken // CSRFトークンをヘッダーに含める
                },
                body: JSON.stringify(shifts), // JSON形式でデータを送信
            })
            .then((response) => {
			    if (!response.ok) {
			        throw new Error('Network response was not ok');
			    }
		    	return response; // レスポンスを取得する
			})
    	}).then((data) => {
            console.log('Shifts saved successfully:', data);
            // データ保存成功後の処理をここに記述
            // 例えば成功メッセージの表示やリダイレクトなど
            alert('シフトが無事保存されました。お疲れ様でした。');
            window.location.href = 'admin'; // リダイレクト先
        })
        .catch((error) => {
            console.error('Error saving shifts:', error);
            // エラーメッセージの表示やエラーハンドリング
            alert('シフトを保存する際にエラーが発生しました。詳細はコンソールをご確認ください。'); //'シフトの保存に成功しました。お疲れ様でした。'
        });
}

function collectShiftData() {
    const rows = document.querySelectorAll('#table-body tr');
    const shifts = [];

    rows.forEach(row => {
        const employee = row.querySelector('td').textContent;
        const dropdowns = row.querySelectorAll('select');

        dropdowns.forEach(dropdown => {
            const shiftData = {
                username: dropdown.dataset.username,
                selectedDate: dropdown.dataset.date,
                shiftType: dropdown.value,
            };
            shifts.push(shiftData);
        });
    });

    return shifts;
}

//トークンの取得
function getAccessToken() {
    return localStorage.getItem('accessToken');
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return `${date.getMonth() + 1}/${date.getDate()}`;
}

function getDayOfWeek(dayIndex) {
    const daysOfWeek = ['日', '月', '火', '水', '木', '金', '土'];
    return daysOfWeek[dayIndex];
}
