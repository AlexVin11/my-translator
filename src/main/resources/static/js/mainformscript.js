document.addEventListener('DOMContentLoaded', function() {
    const targetLanguageSelect = document.getElementById('targetLanguageCode');
    const languageLoading = document.getElementById('languageLoading');
    const translationForm = document.getElementById('translationForm');

    // Функция загрузки языков (оставляем без изменений)
    async function loadLanguages() {
        try {
            languageLoading.style.display = 'inline-block';
            targetLanguageSelect.disabled = true;
            targetLanguageSelect.innerHTML = '<option value="" disabled selected>Загрузка...</option>';

            const response = await fetch('/languages', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                credentials: 'include'
            });

            if (!response.ok) {
                throw new Error(`Ошибка сервера: ${response.status}`);
            }

            const data = await response.json();

            if (!data?.languages || !Array.isArray(data.languages)) {
                throw new Error('Некорректный формат данных');
            }

            populateLanguageSelect(data.languages);
        } catch (error) {
            console.error('Ошибка загрузки языков:', error);
            showError('Ошибка загрузки списка языков');
            targetLanguageSelect.innerHTML = '<option value="" disabled selected>Ошибка загрузки</option>';
        } finally {
            languageLoading.style.display = 'none';
            targetLanguageSelect.disabled = false;
        }
    }

    // Функция заполнения select (оставляем без изменений)
    function populateLanguageSelect(languages) {
        targetLanguageSelect.innerHTML = '';

        languages.sort((a, b) => a.name.localeCompare(b.name))
                 .forEach(lang => {
                     const option = new Option(lang.name, lang.code);
                     targetLanguageSelect.add(option);
                 });

        const russianOption = Array.from(targetLanguageSelect.options)
                                   .find(opt => opt.value === 'ru');
        if (russianOption) {
            targetLanguageSelect.value = 'ru';
        }
    }

    // Обработчик формы (исправленная версия)
    if (translationForm) {
        translationForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            const text = document.getElementById('text').value.trim();
            const targetLanguageCode = targetLanguageSelect.value;

            if (!text || !targetLanguageCode) {
                showError('Заполните все поля');
                return;
            }

            try {
                const token = getCookie('token');
                const headers = {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                };

                if (token) {
                    headers['Authorization'] = `Bearer ${token}`;
                }

                const response = await fetch('/api/translate', {
                    method: 'POST',
                    headers: headers,
                    credentials: 'include',
                    body: JSON.stringify({
                        text: text,
                        targetLanguageCode: targetLanguageCode
                    })
                });

                if (!response.ok) {
                    throw new Error(`Ошибка перевода: ${response.status}`);
                }

                const result = await response.json();
                document.getElementById('translatedText').value =
                    result.translatedText || 'Перевод не получен';

            } catch (error) {
                console.error('Ошибка перевода:', error);
                showError('Ошибка при переводе текста');
            }
        });
    }

    // Функция отображения ошибок (оставляем без изменений)
    function showError(message) {
        const alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-danger position-fixed top-0 end-0 m-3';
        alertDiv.style.zIndex = '1000';
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close float-end" data-bs-dismiss="alert"></button>
        `;
        document.body.appendChild(alertDiv);

        setTimeout(() => {
            if (bootstrap.Alert) {
                const bsAlert = new bootstrap.Alert(alertDiv);
                bsAlert.close();
            } else {
                alertDiv.remove();
            }
        }, 5000);
    }

    // Функция для получения куки (оставляем без изменений)
    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    // Инициализация загрузки языков при старте (это важно!)
    loadLanguages();
});