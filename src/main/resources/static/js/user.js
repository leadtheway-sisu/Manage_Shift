/**
 * userページ｜シフト提出
 */
async function displaySavedDates() {
    try {
        const response = await fetch('/api/saved-dates');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const holidays = await response.json();
        console.log(holidays);

        // カレンダーの日付セルに、選択済みクラスを追加する
        holidays.forEach(holiday => {
            var isoDate = holiday.selectedDates; // データベースから取得した日付（yyyy-mm-dd）
            var [year, month, day] = isoDate.split('-');
            var formattedDate = `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;

            var cell = document.querySelector(`[data-date="${formattedDate}"]`);
            if (cell) {
                cell.classList.add('selected');
            }
        });
    } catch (error) {
        console.error('Error fetching saved dates:', error);
    }
}


  document.addEventListener('DOMContentLoaded', function () {
    displayNextMonth();
    displaySavedDates(); 
  });

  function displayNextMonth() {
    var currentDate = new Date();
    var currentYear = currentDate.getFullYear();
    var currentMonth = currentDate.getMonth();

    // 次の月の年と月を計算
    var nextYear = currentMonth === 11 ? currentYear + 1 : currentYear;
    var nextMonth = currentMonth === 11 ? 0 : currentMonth + 1;

    var monthNames = [
      '1月', '2月', '3月', '4月', '5月', '6月',
      '7月', '8月', '9月', '10月', '11月', '12月'
    ];

    document.getElementById('current-month').textContent = monthNames[nextMonth] + ' ' + nextYear;

    generateCalendar(nextYear, nextMonth);
  }

  function generateCalendar(year, month) {
    var calendarBody = document.getElementById('calendar-body');
    calendarBody.innerHTML = ''; // カレンダーを初期化

    var daysInMonth = new Date(year, month + 1, 0).getDate(); // 次の月の日数
    var firstDayOfMonth = new Date(year, month, 1).getDay(); // 次の月の最初の日の曜日 (0: 日曜〜6: 土曜)

    var date = 1;
    for (var i = 0; i < 6; i++) { // 6週間分の行を生成
      var row = document.createElement('tr');
      for (var j = 0; j < 7; j++) { // 週の日数分のセルを生成
        if (i === 0 && j < firstDayOfMonth) {
          var cell = document.createElement('td');
          row.appendChild(cell);
        } else if (date > daysInMonth) {
          break;
        } else {
          var cell = document.createElement('td');
          cell.textContent = date;
          cell.setAttribute('data-date', year + '-' + ('0' + (month + 1)).slice(-2) + '-' + ('0' + date).slice(-2));　//yyyy-mm-dd型にしている
          cell.addEventListener('click', toggleDateSelection);
          row.appendChild(cell);
          date++;
        }
      }
      calendarBody.appendChild(row);
    }
  }
   // 2024/0628/10:08 ↓
  
var selectedDates = []; // 初期化

function toggleDateSelection() {
    this.classList.toggle('selected');
    var date = this.getAttribute('data-date');

    if (selectedDates.includes(date)) {
        selectedDates = selectedDates.filter(d => d !== date); // 日付が含まれていれば削除
    } else {
        selectedDates.push(date); // 日付を追加
    }
}

function submitShift(event) {
    event.preventDefault();

    getCsrfToken()
        .then(csrfToken => {
            var shiftData = {
                username: document.getElementById('loginuser').textContent,
                selectedDates: selectedDates
            };

            // サーバーにデータを送信する
            fetch('/submitSuccess', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': csrfToken 
                },
                body: JSON.stringify(shiftData),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                console.log('Shift submission successful');
                window.location.href = 'submitSuccess'; // 提出成功後の画面遷移
            })
            .catch(error => {
                console.error('Error submitting shift:', error);
            });
        })
        .catch(error => {
            console.error('Failed to fetch CSRF token:', error);
        });
}


document.addEventListener('DOMContentLoaded', function () {
    displayNextMonth();
    displaySavedDates(); 
});