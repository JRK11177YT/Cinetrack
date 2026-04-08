/* ==========================================
   CineTrack — Main JavaScript
   ========================================== */

document.addEventListener('DOMContentLoaded', () => {

    // ---- Navbar scroll effect ----
    const navbar = document.querySelector('.navbar');
    if (navbar) {
        window.addEventListener('scroll', () => {
            navbar.classList.toggle('scrolled', window.scrollY > 50);
        });
    }

    // ---- Movie Row Carousels ----
    document.querySelectorAll('.movie-row__container').forEach(container => {
        const slider = container.querySelector('.movie-row__slider');
        const leftBtn = container.querySelector('.movie-row__arrow--left');
        const rightBtn = container.querySelector('.movie-row__arrow--right');

        if (!slider) return;

        const scrollAmount = () => slider.clientWidth * 0.75;

        if (rightBtn) {
            rightBtn.addEventListener('click', () => {
                slider.scrollBy({ left: scrollAmount(), behavior: 'smooth' });
            });
        }

        if (leftBtn) {
            leftBtn.addEventListener('click', () => {
                slider.scrollBy({ left: -scrollAmount(), behavior: 'smooth' });
            });
        }
    });

    // ---- Plan Selection ----
    const planCards = document.querySelectorAll('.plan-card');
    const planInput = document.getElementById('planInput');
    if (planCards.length > 0) {
        planCards.forEach(card => {
            card.addEventListener('click', () => {
                planCards.forEach(c => c.classList.remove('selected'));
                card.classList.add('selected');
                if (planInput) {
                    planInput.value = card.dataset.plan;
                }
            });
        });
    }

    // ---- Genre Tag Selection ----
    document.querySelectorAll('.genre-tag').forEach(tag => {
        tag.addEventListener('click', () => {
            tag.classList.toggle('selected');
            const checkbox = tag.querySelector('input[type="checkbox"]');
            if (checkbox) {
                checkbox.checked = tag.classList.contains('selected');
            }
        });
    });

    // ---- Avatar Selection ----
    const avatarOptions = document.querySelectorAll('.avatar-option');
    const avatarInput = document.getElementById('avatarInput');
    if (avatarOptions.length > 0) {
        avatarOptions.forEach(option => {
            option.addEventListener('click', () => {
                avatarOptions.forEach(o => o.classList.remove('selected'));
                option.classList.add('selected');
                if (avatarInput) {
                    avatarInput.value = option.dataset.avatar;
                }
            });
        });
    }

    // ---- Mi Lista (Add/Remove) ----
    document.querySelectorAll('[data-milista-toggle]').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            e.preventDefault();
            e.stopPropagation();
            const peliculaId = btn.dataset.peliculaId;
            const isInList = btn.classList.contains('active');

            const url = isInList
                ? `/mi-lista/eliminar/${peliculaId}`
                : `/mi-lista/agregar/${peliculaId}`;
            const method = isInList ? 'DELETE' : 'POST';

            try {
                // Get CSRF token
                const csrfMeta = document.querySelector('meta[name="_csrf"]');
                const csrfHeaderMeta = document.querySelector('meta[name="_csrf_header"]');
                const headers = { 'Content-Type': 'application/json' };
                if (csrfMeta && csrfHeaderMeta) {
                    headers[csrfHeaderMeta.content] = csrfMeta.content;
                }

                const response = await fetch(url, { method, headers });
                if (response.ok) {
                    btn.classList.toggle('active');
                    const icon = btn.querySelector('i');
                    if (icon) {
                        icon.className = btn.classList.contains('active')
                            ? 'fas fa-check'
                            : 'fas fa-plus';
                    }
                }
            } catch (error) {
                console.error('Error al actualizar Mi Lista:', error);
            }
        });
    });

    // ---- Password Visibility Toggle ----
    document.querySelectorAll('.toggle-password').forEach(btn => {
        btn.addEventListener('click', () => {
            const input = btn.closest('.form-group').querySelector('input');
            const icon = btn.querySelector('i');
            if (input.type === 'password') {
                input.type = 'text';
                icon.className = 'fas fa-eye-slash';
            } else {
                input.type = 'password';
                icon.className = 'fas fa-eye';
            }
        });
    });

});
