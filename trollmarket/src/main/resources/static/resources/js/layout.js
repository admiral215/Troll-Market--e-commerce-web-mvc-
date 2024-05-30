(function(){
addActiveNavListener();
addValueToUpsert();
addValueToInfoModal();
addValueToInfoShopModal();
addListenerModalCart();
addListenerShipmentUpsert();
}())

function addActiveNavListener() {
  const navItems = document.querySelectorAll('.nav-item');

  navItems.forEach(navItem => {
    navItem.addEventListener('click', function() {
      navItems.forEach(nav => nav.classList.remove('active'));
      this.classList.add('active');
    });
  });
};

function addUpsertModalListener() {
  document.addEventListener('DOMContentLoaded', function() {
    const upsertButtons = document.querySelectorAll('.upsert-button');

    upsertButtons.forEach(function(button) {
      button.addEventListener('click', function(event) {
        event.preventDefault();
        let name = this.getAttribute('data-name');
        let upsertForm = document.querySelector('#upsert');
        upsertForm.setAttribute('action', name);
      });
    });
  });
}

function addValueToUpsert(){
  document.addEventListener('DOMContentLoaded', function() {
    const upsertButtons = document.querySelectorAll('.upsert-button');
    const upsertForm = document.querySelector('#upsert');

    upsertButtons.forEach(function(button) {
      button.addEventListener('click', function(event) {
        event.preventDefault();

        upsertForm.querySelector('#name').value = '';
        upsertForm.querySelector('#category').value = '';
        upsertForm.querySelector('#description').value = '';
        upsertForm.querySelector('#price').value = '';
        upsertForm.querySelector('#discontinueYes').checked = false;
    
        let name = this.getAttribute('data-name');
        let category = this.getAttribute('data-category');
        let product = this.getAttribute('data-product')
        let description = this.getAttribute('data-description');
        let price = this.getAttribute('data-price');
        let discontinue = this.getAttribute('data-discontinue') === 'Yes';
  

        upsertForm.setAttribute('action', name);
  
        upsertForm.querySelector('#name').value = product;
        upsertForm.querySelector('#category').value = category;
        upsertForm.querySelector('#description').value = description;
        upsertForm.querySelector('#price').value = price;
        upsertForm.querySelector('#discontinueYes').checked = discontinue;

      });
    });
  });
  
}


function addValueToInfoModal(){
  document.addEventListener('DOMContentLoaded', function() {
    const infoButtons = document.querySelectorAll('.info-button');
  
    infoButtons.forEach(function(button) {
      button.addEventListener('click', function(event) {
        event.preventDefault();
  
        let name = this.getAttribute('data-product');
        let category = this.getAttribute('data-category');
        let description = this.getAttribute('data-description');
        let price = this.getAttribute('data-price');
        let discontinue = this.getAttribute('data-discontinue') === 'true';
  
        document.getElementById('info-name').textContent = name;
        document.getElementById('info-category').textContent = category;
        document.getElementById('info-description').textContent = description;
        document.getElementById('info-price').textContent = price;
        document.getElementById('info-discontinue').textContent = discontinue ? 'Yes' : 'No';
      });
    });
  });
  
}

function addValueToInfoShopModal(){
  document.addEventListener('DOMContentLoaded', function() {
    const infoButtons = document.querySelectorAll('.shop-info-button');

    infoButtons.forEach(function(button) {
      button.addEventListener('click', function(event) {
        event.preventDefault();

        // Get product data from button's data attributes
        let id = this.getAttribute('data-id');
        let name = this.getAttribute('data-product');
        let category = this.getAttribute('data-category');
        let description = this.getAttribute('data-description');
        let price = this.getAttribute('data-price');
        let seller = this.getAttribute('data-seller');

        // Fill the table with product data
        document.getElementById('info-name').textContent = name;
        document.getElementById('info-category').textContent = category;
        document.getElementById('info-description').textContent = description;
        document.getElementById('info-price').textContent = price;
        document.getElementById('info-seller').textContent = seller;
      });
    });
  });
}

function addListenerModalCart(){
  document.addEventListener('DOMContentLoaded', function() {
    const cartButtons = document.querySelectorAll('.shop-cart-button');
    const cartForm = document.querySelector('.cart-form');

    cartButtons.forEach(function(button) {
      button.addEventListener('click', function(event) {
        event.preventDefault();

        let action = this.getAttribute('data-name');

        cartForm.setAttribute('action', action);
      });
    });
  });

}

function addListenerShipmentUpsert(){
  document.addEventListener('DOMContentLoaded', (event) => {
    const insertButton = document.querySelector('.insert-shipment-button');
    const editButtons = document.querySelectorAll('.update-shipment-button');
    const form = document.querySelector('#upsert-shipment-form');
    const serviceInput = form.querySelector('#serviceYes');
    
    if (insertButton) {
        insertButton.addEventListener('click', () => {
            form.reset();  // Clear all form fields
            form.querySelector('#id').value = '';  // Set id to null
            serviceInput.disabled = false;
        });
    }

    editButtons.forEach(editButton => {
        editButton.addEventListener('click', () => {
            const id = editButton.getAttribute('data-id');
            const name = editButton.getAttribute('data-name');
            const price = editButton.getAttribute('data-price');
            const isService = editButton.getAttribute('data-isService');

            form.querySelector('#id').value = id;
            form.querySelector('#name').value = name;
            form.querySelector('#price').value = price;

            if (isService === 'false') {
                serviceInput.checked = false;
                serviceInput.disabled = true;
            } else {
              serviceInput.disabled = false;
              serviceInput.checked = true;
            }
        });
    });
});
}