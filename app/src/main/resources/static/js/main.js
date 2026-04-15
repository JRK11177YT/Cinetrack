/* ==========================================
   CineTrack — Premium JavaScript
   Bootstrap 5 + Animations
   ========================================== */

document.addEventListener('DOMContentLoaded', () => {

    /* ================================================
       1. NAVBAR SCROLL EFFECT
       ================================================ */
    const navbar = document.querySelector('.ct-navbar');
    if (navbar) {
        const updateNavbar = () => {
            navbar.classList.toggle('scrolled', window.scrollY > 50);
        };
        window.addEventListener('scroll', updateNavbar, { passive: true });
        updateNavbar();
    }

    /* ================================================
       2. SCROLL REVEAL ANIMATIONS
       Intersection Observer for .reveal elements
       ================================================ */
    const revealElements = document.querySelectorAll('.reveal');
    if (revealElements.length > 0) {
        const revealObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('visible');
                    revealObserver.unobserve(entry.target);
                }
            });
        }, {
            threshold: 0.1,
            rootMargin: '0px 0px -40px 0px'
        });

        revealElements.forEach(el => revealObserver.observe(el));
    }

    /* ================================================
       3. MOVIE ROW CAROUSELS
       Smooth horizontal scroll with arrows
       ================================================ */
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

        // Arrow visibility based on scroll position
        const updateArrows = () => {
            if (leftBtn) {
                leftBtn.style.opacity = slider.scrollLeft > 10 ? '1' : '0';
                leftBtn.style.pointerEvents = slider.scrollLeft > 10 ? 'auto' : 'none';
            }
            if (rightBtn) {
                const atEnd = slider.scrollLeft + slider.clientWidth >= slider.scrollWidth - 10;
                rightBtn.style.opacity = atEnd ? '0' : '1';
                rightBtn.style.pointerEvents = atEnd ? 'none' : 'auto';
            }
        };

        slider.addEventListener('scroll', updateArrows, { passive: true });
        updateArrows();
    });

    /* ================================================
       4. MOVIE CARD HOVER EFFECTS
       Scale + glow on hover with stagger
       ================================================ */
    document.querySelectorAll('.movie-card').forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.zIndex = '10';
        });
        card.addEventListener('mouseleave', function() {
            setTimeout(() => { this.style.zIndex = ''; }, 300);
        });
    });

    /* ================================================
       4b. NOVEDADES CAROUSEL
       Auto-rotating hero carousel with timer
       ================================================ */
    const novedadesContainer = document.getElementById('novedadesCarousel');
    if (novedadesContainer) {
        const slides = novedadesContainer.querySelectorAll('.ct-novedades__slide');
        const dots = novedadesContainer.querySelectorAll('.ct-novedades__dot');
        const prevBtn = document.getElementById('novedadesPrev');
        const nextBtn = document.getElementById('novedadesNext');
        const timerBar = document.getElementById('novedadesTimer');
        let currentSlide = 0;
        let autoInterval = null;
        let timerAnimation = null;
        const SLIDE_DURATION = 8000; // ms

        function goToSlide(index) {
            // Pause current video
            slides[currentSlide].querySelectorAll('video').forEach(v => { v.pause(); v.currentTime = 0; });
            slides[currentSlide].classList.remove('active');
            if (dots[currentSlide]) dots[currentSlide].classList.remove('active');

            currentSlide = (index + slides.length) % slides.length;

            slides[currentSlide].classList.add('active');
            if (dots[currentSlide]) dots[currentSlide].classList.add('active');

            // Play video on active slide
            const video = slides[currentSlide].querySelector('video');
            if (video) {
                video.currentTime = 0;
                video.play().catch(() => {});
            }

            resetTimer();
        }

        function nextSlide() { goToSlide(currentSlide + 1); }
        function prevSlide() { goToSlide(currentSlide - 1); }

        function resetTimer() {
            if (timerBar) {
                timerBar.style.transition = 'none';
                timerBar.style.width = '0%';
                requestAnimationFrame(() => {
                    requestAnimationFrame(() => {
                        timerBar.style.transition = `width ${SLIDE_DURATION}ms linear`;
                        timerBar.style.width = '100%';
                    });
                });
            }
            clearInterval(autoInterval);
            autoInterval = setInterval(nextSlide, SLIDE_DURATION);
        }

        if (prevBtn) prevBtn.addEventListener('click', prevSlide);
        if (nextBtn) nextBtn.addEventListener('click', nextSlide);

        dots.forEach(dot => {
            dot.addEventListener('click', () => {
                goToSlide(parseInt(dot.dataset.index, 10));
            });
        });

        // Start first video & timer
        if (slides.length > 0) {
            const firstVideo = slides[0].querySelector('video');
            if (firstVideo) firstVideo.play().catch(() => {});
            if (slides.length > 1) resetTimer();
        }

        // Pause on hover
        novedadesContainer.addEventListener('mouseenter', () => clearInterval(autoInterval));
        novedadesContainer.addEventListener('mouseleave', () => {
            if (slides.length > 1) autoInterval = setInterval(nextSlide, SLIDE_DURATION);
        });
    }

    /* ================================================
       4c. AUTH PARTICLES
       Floating particle animation for login/register
       ================================================ */
    const particleContainer = document.getElementById('authParticles');
    if (particleContainer) {
        for (let i = 0; i < 30; i++) {
            const p = document.createElement('div');
            p.className = 'particle';
            p.style.left = Math.random() * 100 + '%';
            p.style.animationDuration = (6 + Math.random() * 10) + 's';
            p.style.animationDelay = (Math.random() * 8) + 's';
            p.style.width = p.style.height = (1 + Math.random() * 3) + 'px';
            if (Math.random() > 0.7) p.style.background = 'rgba(99,102,241,0.3)';
            particleContainer.appendChild(p);
        }
    }

    /* ================================================
       5. PLAN CARD SELECTION
       ================================================ */
    const planCards = document.querySelectorAll('.plan-card');
    if (planCards.length > 0) {
        planCards.forEach(card => {
            card.addEventListener('click', () => {
                planCards.forEach(c => c.classList.remove('selected'));
                card.classList.add('selected');

                // Update hidden radio
                const radio = card.querySelector('input[type="radio"]');
                if (radio) {
                    radio.checked = true;
                }

                // Also support legacy planInput
                const planInput = document.getElementById('planInput');
                if (planInput) {
                    planInput.value = card.dataset.plan;
                }

                // Ripple effect
                createRipple(card, null);
            });
        });
    }

    /* ================================================
       6. GENRE TAG SELECTION
       ================================================ */
    document.querySelectorAll('.genre-tag').forEach(tag => {
        tag.addEventListener('click', () => {
            tag.classList.toggle('selected');

            const checkbox = tag.querySelector('input[type="checkbox"]');
            if (checkbox) {
                checkbox.checked = tag.classList.contains('selected');
            }

            // Update hidden generos input
            updateGenresInput();
        });
    });

    function updateGenresInput() {
        const selected = document.querySelectorAll('.genre-tag.selected');
        const ids = Array.from(selected).map(t => t.dataset.id).filter(Boolean);

        // Legacy: update CSV hidden input if present
        const csvInput = document.getElementById('generosInput');
        if (csvInput) csvInput.value = ids.join(',');

        // Create one hidden input[name="generoIds"] per selected genre
        const form = document.querySelector('form#profileForm');
        if (form) {
            form.querySelectorAll('input[name="generoIds"]').forEach(i => i.remove());
            ids.forEach(id => {
                const inp = document.createElement('input');
                inp.type = 'hidden';
                inp.name = 'generoIds';
                inp.value = id;
                form.appendChild(inp);
            });
        }

        // Update hint text if present
        const hint = document.getElementById('genreHint');
        if (hint) {
            hint.classList.remove('error');
            hint.textContent = ids.length === 0
                ? 'Ningún género seleccionado'
                : ids.length === 1 ? '1 género seleccionado' : ids.length + ' géneros seleccionados';
        }
    }

    /* ================================================
       7. AVATAR SELECTION
       ================================================ */
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

    /* ================================================
       8. MI LISTA (Add/Remove) — AJAX
       ================================================ */
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

                    // Micro-animation feedback
                    btn.style.transform = 'scale(1.3)';
                    setTimeout(() => { btn.style.transform = ''; }, 200);
                }
            } catch (error) {
                console.error('Error al actualizar Mi Lista:', error);
            }
        });
    });

    /* ================================================
       9. PASSWORD VISIBILITY TOGGLE
       ================================================ */
    document.querySelectorAll('.toggle-password').forEach(btn => {
        btn.addEventListener('click', () => {
            const group = btn.closest('.ct-form-group, .position-relative, .auth-field__input-wrap');
            const input = group ? group.querySelector('input[type="password"], input[type="text"]') : null;
            if (!input) return;
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

    /* ================================================
       10. ANIMATED COUNTERS
       For dashboard stat numbers
       ================================================ */
    document.querySelectorAll('.admin-stat-card__number').forEach(el => {
        const target = parseInt(el.textContent, 10);
        if (isNaN(target) || target === 0) return;

        const counterObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    animateCounter(el, 0, target, 1200);
                    counterObserver.unobserve(el);
                }
            });
        }, { threshold: 0.5 });

        el.textContent = '0';
        counterObserver.observe(el);
    });

    function animateCounter(el, start, end, duration) {
        const startTime = performance.now();
        const easeOutQuart = t => 1 - Math.pow(1 - t, 4);

        function update(currentTime) {
            const elapsed = currentTime - startTime;
            const progress = Math.min(elapsed / duration, 1);
            const value = Math.round(start + (end - start) * easeOutQuart(progress));
            el.textContent = value.toLocaleString('es-ES');

            if (progress < 1) {
                requestAnimationFrame(update);
            }
        }

        requestAnimationFrame(update);
    }

    /* ================================================
       11. RIPPLE EFFECT
       ================================================ */
    function createRipple(element, event) {
        const ripple = document.createElement('span');
        ripple.classList.add('ct-ripple');
        const rect = element.getBoundingClientRect();
        const size = Math.max(rect.width, rect.height);
        ripple.style.width = ripple.style.height = size + 'px';

        if (event) {
            ripple.style.left = (event.clientX - rect.left - size / 2) + 'px';
            ripple.style.top = (event.clientY - rect.top - size / 2) + 'px';
        } else {
            ripple.style.left = (rect.width / 2 - size / 2) + 'px';
            ripple.style.top = (rect.height / 2 - size / 2) + 'px';
        }

        element.style.position = 'relative';
        element.style.overflow = 'hidden';
        element.appendChild(ripple);
        setTimeout(() => ripple.remove(), 600);
    }

    // Add ripple to accent buttons
    document.querySelectorAll('.btn-ct-accent, .btn-ct-play').forEach(btn => {
        btn.addEventListener('click', function(e) {
            createRipple(this, e);
        });
    });

    /* ================================================
       12. HERO PARALLAX
       Subtle parallax on hero background
       ================================================ */
    const heroBg = document.querySelector('.ct-hero__bg');
    if (heroBg) {
        window.addEventListener('scroll', () => {
            const scrollY = window.scrollY;
            if (scrollY < window.innerHeight) {
                heroBg.style.transform = `scale(1.1) translateY(${scrollY * 0.15}px)`;
            }
        }, { passive: true });
    }

    /* ================================================
       13. SMOOTH PAGE TRANSITIONS
       Fade-in effect on page load
       ================================================ */
    document.body.style.opacity = '0';
    document.body.style.transition = 'opacity 0.4s ease';
    requestAnimationFrame(() => {
        requestAnimationFrame(() => {
            document.body.style.opacity = '1';
        });
    });

    /* ================================================
       14. CUENTA PLAN SELECTION
       ================================================ */
    document.querySelectorAll('.cuenta-plan-option').forEach(option => {
        option.addEventListener('click', () => {
            document.querySelectorAll('.cuenta-plan-option').forEach(o => {
                o.classList.remove('cuenta-plan-option--active');
            });
            option.classList.add('cuenta-plan-option--active');
            const radio = option.querySelector('input[type="radio"]');
            if (radio) radio.checked = true;
        });
    });

    /* ================================================
       15. TOOLTIP INIT (Bootstrap)
       ================================================ */
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    [...tooltipTriggerList].map(el => new bootstrap.Tooltip(el));

    /* ================================================
       16. AUTO-DISMISS ALERTS
       ================================================ */
    document.querySelectorAll('.alert, .admin-alert').forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-10px)';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    });

});
