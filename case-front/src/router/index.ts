import { createRouter, createWebHistory } from 'vue-router'

import LoginView from '../views/public/LoginView.vue'
import DashboardView from '../views/private/DashboardView.vue'
import NotFoundView from '../views/public/NotFoundView.vue'
import LandingView from '../views/public/LandingView.vue'
import RegisterView from '../views/public/RegisterView.vue'
import UserFormView from '../views/private/users/UserFormView.vue'
import AddressFormView from '../views/private/addresses/AddressFormView.vue'
import AddressesView from '../views/private/addresses/AddressView.vue'

const routes = [
  {
    path: '/',
    name: 'Landing',
    component: LandingView,
    meta: {
      public: true
    }
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterView,
    meta: {
      public: true
    }
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
    meta: {
      public: true
    }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: DashboardView,
    meta: {
      requiresAuth: true
    },
    children: [
      {
        path: 'users/new',
        name: 'NewUser',
        component: UserFormView,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'users/:id',
        name: 'ViewUser',
        component: UserFormView,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'users/:id/edit',
        name: 'EditUser',
        component: UserFormView,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'users/:id/addresses',
        name: 'UserAddresses',
        component: AddressesView,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'users/:id/addresses/new',
        name: 'NewUserAddress',
        component: AddressFormView,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'users/:id/addresses/:addressId/edit',
        name: 'EditUserAddress',
        component: AddressFormView,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: 'users/:id/addresses/:addressId',
        name: 'ViewUserAddress',
        component: AddressFormView,
        meta: {
          requiresAuth: true
        }
      }
    ]
  }, 
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFoundView,
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  const token = localStorage.getItem('access_token')

  if (to.meta.requiresAuth && !token) {
    return '/login'
  }

  if (to.path === '/login' && token) {
    return '/dashboard'
  }
})

export default router